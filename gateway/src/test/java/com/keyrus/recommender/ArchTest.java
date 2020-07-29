package com.keyrus.recommender;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.keyrus.recommender");

        noClasses()
            .that()
                .resideInAnyPackage("com.keyrus.recommender.service..")
            .or()
                .resideInAnyPackage("com.keyrus.recommender.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.keyrus.recommender.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
