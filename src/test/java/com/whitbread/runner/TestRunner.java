package com.whitbread.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


/**
 * The Class TestRunner to glue the feature files and step definitions and also generate the Cucumber reports.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features",
glue={"com/whitbread/stepDefinitions"},
plugin = {"de.monochromata.cucumber.report.PrettyReports:target/cucumber"},
tags = {"@postive"},
monochrome = true)

public class TestRunner {


}
