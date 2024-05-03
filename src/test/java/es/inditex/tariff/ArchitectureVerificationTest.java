package es.inditex.tariff;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses(packages = "es.inditex.tariff")
public class ArchitectureVerificationTest {
    @ArchTest
    ArchRule dddRules = JMoleculesDddRules.all();
    @ArchTest ArchRule onion = JMoleculesArchitectureRules.ensureHexagonal();
}
