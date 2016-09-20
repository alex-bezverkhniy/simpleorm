package com.simpleorm.core

import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection


class StandardDaoIntSpec extends Specification {

    StandardDao<TestEntity> sut

    @Shared
    Connection conn;

    def setup() {
        sut = Dao.getInstance(TestEntity)
    }

    def cleanup() {
        sut.getConnection().prepareStatement("TRUNCATE TABLE test")
    }

    def "loadDefaultDbProperties should load default properties"() {
        when:
        def prop = sut.loadDefaultDbProperties()

        then:
        prop

    }

    def "getConnection should return default connections"() {
        when:
        def conn = sut.getConnection()
        then:
        conn
    }


    def "Create should insert new record"() {
        setup:
        TestEntity testEntity = new TestEntity(name: "Alex")

        when:
        TestEntity result = sut.create(testEntity)

        then:
        result
        result.name == testEntity.name
    }
/*
    def "Create3"() {

    }

    def "Find"() {

    }

    def "Find1"() {

    }

    def "FindAll"() {

    }

    def "FindAll1"() {

    }

    def "FindAllAsMap"() {

    }

    def "FindAllAsMap1"() {

    }

    def "FindById"() {

    }

    def "FindById1"() {

    }

    def "Update"() {

    }

    def "Update1"() {

    }

    def "Delete"() {

    }

    def "Delete1"() {

    }

    def "Delete2"() {

    }

    def "Delete3"() {

    }

    def "DeleteById"() {

    }

    def "DeleteById1"() {

    }
    */
}
