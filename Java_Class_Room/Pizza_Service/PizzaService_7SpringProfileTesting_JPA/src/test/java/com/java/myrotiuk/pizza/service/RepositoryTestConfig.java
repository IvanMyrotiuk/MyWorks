package com.java.myrotiuk.pizza.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/repositoryContext.xml",
		//"classpath:/repositoryTestContext.xml"
		"classpath:/childRepoTestContext.xml"
})
@ActiveProfiles(profiles = "dev")
public class RepositoryTestConfig {

}