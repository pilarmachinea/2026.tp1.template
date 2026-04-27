package com.BiblioTech.model;

import java.time.LocalDate;

public record Prestamo(
        String isbn,
        String dniSocio,
        LocalDate fechaPrestamo,
        LocalDate fechaVencimiento,
        LocalDate fechaDevolucion
) {
    private static final int DIAS_PRESTAMO = 14;

    public static Prestamo crear(String isbn, String dniSocio) {
        LocalDate hoy = LocalDate.now();
        return new Prestamo(isbn, dniSocio, hoy, hoy.plusDays(DIAS_PRESTAMO), null);
    }

    public Prestamo conDevolucion() {
        return new Prestamo(isbn, dniSocio, fechaPrestamo, fechaVencimiento, LocalDate.now());
    }

    public boolean estaDevuelto() {
        return fechaDevolucion != null;
    }

    public long calcularDiasRetraso() {
        if (!estaDevuelto()) return 0;
        long retraso = fechaVencimiento.until(fechaDevolucion).getDays();
        return Math.max(0, retraso);
    }
}
