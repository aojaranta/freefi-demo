package fi.free;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import fi.free.utils.SSNUtil;

@SpringBootTest
class DemoApplicationTests {
	
	@Mock
    private RestTemplate restTemplate;
	
	@Test
	void contextLoads() {
	}
	
	@DisplayName("Verifies that real SSN is accepted")
	@Test
	void testRealSocialSecurityNumberIsValid() {
		assertTrue(SSNUtil.validateSSN("150285-537J"));
		assertTrue(SSNUtil.validateSSN("040873-155V"));
		assertTrue(SSNUtil.validateSSN("190501A6362"));
		assertTrue(SSNUtil.validateSSN("200200+808K"));
	}
	
	@DisplayName("Verifies that fake SSN is not accepted")
	@Test
	void testFakeSocialSecurityNumberIsNotValid() {
		assertFalse(SSNUtil.validateSSN("123456-789A"));
	}
	
	@DisplayName("Verifies that SSN validation is returning value even if invalid data format")
	@Test
	void testInvalidSocialSecurityNumberIsNotThrowingError() {
		assertFalse(SSNUtil.validateSSN("ABR123456-789A"));
		assertFalse(SSNUtil.validateSSN("356-789A"));
		assertFalse(SSNUtil.validateSSN("ABR123456A789A"));
		assertFalse(SSNUtil.validateSSN("!pr35Ay754--"));
	}

}
