import com.netaporter.pricing.PricingApplication;
import com.netaporter.pricing.domain.Product;
import com.netaporter.pricing.domain.ProductPricingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.stream.Stream;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by j.hoare on 4/15/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = PricingApplication.class)
@WebAppConfiguration
public class ProductPricingControllerTest {


    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ProductPricingRepository productPricingRepository;

    @Test
    public void testRoutes() {
        final MockMvc mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();

        Stream.of("/products").forEach(path -> {
            try {
                mockMvc
                        .perform(get(path))
                        .andExpect(status().is5xxServerError())
                        .andExpect(content().contentType("application/hal+json"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

    @Test
    public void invokesDefaultMethod() {

        //Product product = productPricingRepository.save(new Product("shoes", "jimmy","jimmy choo"));
        Optional<Product> result = productPricingRepository.findProduct(new Long(1));

        assertThat(result.isPresent(), is(true));

    }


}




