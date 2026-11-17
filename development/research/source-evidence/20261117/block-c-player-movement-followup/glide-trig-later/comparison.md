# Elytra look-vector trig cutover: later source coverage

Scope: canonical Mojmap decompilations for 1.20.6, 1.21, 1.21.1 through 1.21.11. This closes the later-source interval only; the supplied prior audit's 1.14.4 comparison was not re-opened. No Elytra activation or gravity behavior was evaluated.

## Finding

`Entity.getLookAngle()` delegates to `Entity.calculateViewVector(this.getXRot(), this.getYRot())` throughout this release set. `calculateViewVector(float, float)` is otherwise textually unchanged in the inspected releases: it converts each angle to a `float` radian value using `(float) (Math.PI / 180.0)`, calls `Mth.cos`/`Mth.sin`, and constructs `Vec3` from float products (widened to the vector constructor's doubles).

The trig lookup overload and index arithmetic change only in 1.21.11 among the inspected releases:

- 1.20.6 through 1.21.10: `Mth` has `float[] SIN`, `sin(float)` and `cos(float)`, with float scale `10430.378F`; lookup indices use `(int)(angle * scale)` and a 16,384F cosine offset, masked with `65535`.
- 1.21.11: `Mth` still stores lookup results in `float[] SIN`, but its scale is `double 10430.378350470453`, and `sin(double)` / `cos(double)` use `(int)((long)(angle * scale [+ 16384.0]) & 65535L)`. The float radian values produced by `calculateViewVector` are widened to double at these calls. Returned trig values and subsequent products remain float.

Therefore this source set establishes the cutover between 1.21.10 and 1.21.11. It does not narrow the historical introduction date earlier than this adjacent-patch boundary.

## Elytra caller path

`LivingEntity` supplies the same look-angle input to the Elytra movement math in every inspected release. In 1.20.6–1.21.1 the call is inside `LivingEntity.travel`; from 1.21.2 onward it is in `updateFallFlyingMovement`, called by the travel path. The extracted method still begins with `Vec3 look = this.getLookAngle()` (decompiler-local name varies). This records only the caller/provider path; no activation or gravity gates are assessed here.

## Intermediate coverage

For each release below, `Entity.calculateViewVector` uses float radians and Mth float overloads; `Mth` retains the float-scale lookup arithmetic; and the Elytra caller passes `getLookAngle()` into that provider path. The individual canonical files are hash-checked against their local outer source manifest or nested provenance `sources` entry.

| Release | Source status | Mth trig API | Elytra caller location |
|---|---|---|---|
| 1.20.6 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.travel` |
| 1.21 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.travel` |
| 1.21.1 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.travel` |
| 1.21.2 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.3 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.4 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.5 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.6 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.7 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.8 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.9 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.10 | present; three relevant source hashes match | `float` / float scale | `LivingEntity.updateFallFlyingMovement` |
| 1.21.11 | present; three relevant source hashes match | `double` / double scale | `LivingEntity.updateFallFlyingMovement` |

## Exact source references

- 1.20.6: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/Entity.java:1479` (`calculateViewVector`); `.../LivingEntity.java:2171` (`getLookAngle` caller); `decompiled_minecraft/1.20.6/mojmap/net/minecraft/util/Mth.java:32,48-54` (float table and overloads).
- 1.21.10: `decompiled_minecraft/1.21.10/mojmap/net/minecraft/util/Mth.java:32,48-54` (last float overload release).
- 1.21.11: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java:1828` (`calculateViewVector`); `.../LivingEntity.java:2463` (`getLookAngle` caller); `decompiled_minecraft/1.21.11/mojmap/net/minecraft/util/Mth.java:33-56` (double scale and overloads).

All requested canonical source files were present. No source had to be regenerated; no required source is missing. For releases with outer `*.sources.sha256`, the SHA-256 entries for `Entity.java`, `LivingEntity.java`, and `Mth.java` matched. For releases without that outer manifest, the same three files matched the hidden `mojmap/.decompile-provenance.json` `sources` entries. Outer provenance identifies releases/mapping as `versionId` plus `namespace`; nested provenance exists for the remaining requested releases and its pinned input/source metadata was checked locally.