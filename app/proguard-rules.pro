# =====================================================================================
# Reglas de ProGuard/R8 para la aplicación TimeTracking
# =====================================================================================

# 1. Conservar los Modelos de Datos (entidades, DTOs, etc)
-keep class com.budoxr.ett.data.dtos.** { <fields>; }
-keep class com.budoxr.ett.presentation.domain.** { <fields>; }

# 2. Conservar Atributos para Librerías de Reflexión
# Permite que librerías como Gson puedan leer metadatos (genéricos, anotaciones)
# de las clases en tiempo de ejecución.
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses

# 4. Kotlin Coroutines
# Previene problemas con la maquinaria interna de las corrutinas en builds de release.
-keepnames class kotlinx.coroutines.internal.** { *; }
-keep class kotlinx.coroutines.flow.** { *; }
-keepnames class kotlinx.coroutines.DefaultExecutor

# 5. Koin (Opcional si usas KSP)
# Koin con KSP (`koin-ksp-compiler`)  debería generar estas reglas automáticamente.
# Se incluyen aquí como una medida de seguridad adicional.
-keep class org.koin.** { *; }
-keep class io.insert.koin.** { *; }
-dontwarn org.koin.**
-dontwarn io.insert.koin.**

# 6. Room
# Al igual que Koin, el procesador de anotaciones de Room  debería manejar esto,
# pero no está de más ser explícito.
-keep class androidx.room.RoomDatabase** { *; }

# --- FIN DE LAS REGLAS ---