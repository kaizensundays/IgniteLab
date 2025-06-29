package com.kaizensundays.flights.service

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.Test

/**
 * Created: Saturday 4/19/2025, 5:38 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class ArchTest {

    private fun classes() = ClassFileImporter().importPackages("com.kaizensundays")

    @Test
    fun test() {

        val classes = classes()

        noClasses()
            .that()
            .resideInAPackage("com.kaizensundays.ignite..")
            .should()
            .accessClassesThat().resideInAPackage("com.kaizensundays.flights..")
            .check(classes)

    }

}