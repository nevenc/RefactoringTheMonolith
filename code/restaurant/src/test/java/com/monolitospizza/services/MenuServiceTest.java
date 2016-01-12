package com.monolitospizza.services;

import com.monolitospizza.model.Crust;
import com.monolitospizza.model.Pizza;
import com.monolitospizza.model.Sauce;
import com.monolitospizza.model.Size;
import com.monolitospizza.repositories.CrustRepository;
import com.monolitospizza.repositories.SauceRepository;
import com.monolitospizza.repositories.SizeRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Matt Stine
 */
public class MenuServiceTest {

    private CrustRepository mockCrustRepository;
    private SauceRepository mockSauceRepository;
    private SizeRepository mockSizeRepository;
    private MenuService menuService;

    @Before
    public void setUp() throws Exception {
        mockCrustRepository = mock(CrustRepository.class);
        mockSauceRepository = mock(SauceRepository.class);
        mockSizeRepository = mock(SizeRepository.class);
        menuService = new MenuService(mockSizeRepository,
                mockCrustRepository,
                mockSauceRepository);
    }

    @Test
    public void shouldLoadBasePizzaMenuOptions() {
        when(mockSizeRepository.findAll())
                .thenReturn(Arrays.asList(new Size("Large", BigDecimal.ZERO)));

        when(mockCrustRepository.findAll())
                .thenReturn(Arrays.asList(new Crust("Thin")));

        when(mockSauceRepository.findAll())
                .thenReturn(Arrays.asList(new Sauce("Normal")));

        BasePizzaMenuOptions baseMenuOptions =  menuService.loadBasePizzaMenuOptions();

        assertThat(baseMenuOptions.getSizes().iterator().hasNext(), is(true));
        assertThat(baseMenuOptions.getCrusts().iterator().hasNext(), is(true));
        assertThat(baseMenuOptions.getSauces().iterator().hasNext(), is(true));
    }

    @Test
    public void shouldLoadDefaultPizzaConfiguration() {
        when(mockSizeRepository.findOneByName("Large"))
                .thenReturn(new Size("Large", BigDecimal.ZERO));
        when(mockCrustRepository.findOneByName("Thin"))
                .thenReturn(new Crust("Thin"));
        when(mockSauceRepository.findOneByName("Normal"))
                .thenReturn(new Sauce("Normal"));

        Pizza pizza = menuService.loadDefaultPizzaConfiguration();

        assertThat(pizza.getSize(), is(equalTo(new Size("Large", BigDecimal.ZERO))));
        assertThat(pizza.getCrust(), is(equalTo(new Crust("Thin"))));
        assertThat(pizza.getSauce(), is(equalTo(new Sauce("Normal"))));
    }
}
