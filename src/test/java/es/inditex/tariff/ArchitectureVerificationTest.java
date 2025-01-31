package es.inditex.tariff;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "es.inditex.tariff")
public class ArchitectureVerificationTest {
    @ArchTest
    ArchRule dddRules = JMoleculesDddRules.all();
    @ArchTest
    ArchRule hex = JMoleculesArchitectureRules.ensureHexagonal();
    @ArchTest
    ArchRule onion = JMoleculesArchitectureRules.ensureOnionSimple();
}
