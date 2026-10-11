package com.demo.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaUtil {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("defaultPU");

    private JpaUtil() { }
    public static EntityManagerFactory getFactory() { return FACTORY; }
    public static void shutdown() {
        if (FACTORY.isOpen()) FACTORY.close();
    }
}