# Correction 2: release-exact point-query helper names

This additive note refines `correction-1-client-world-gate.md`, which remains bound to the source review and client-world audit. Its phrase “`Entity.isSubmergedIn(tag, true)` in 1.14.x” is a family description, not an exact member name on every source side. The exact check-loaded helper aliases are:

| Release / mapping | Exact helper |
|---|---|
| 1.14 Yarn | `Entity.isInFluid(Tag<Fluid>, boolean)`, line 986 |
| 1.14.1 Yarn | `Entity.isInFluid(Tag<Fluid>, boolean)`, line 986 |
| 1.14.2 Yarn | `Entity.isInFluid(Tag<Fluid>, boolean)`, line 988 |
| 1.14.3 Yarn | `Entity.isSubmergedIn(Tag<Fluid>, boolean)`, line 1032 |
| 1.14.4 Ornithe Feather | `Entity.m_69330161(Tag<Fluid>, boolean)`, line 1039 |

In 1.14.4, the public single-argument `isSubmergedIn(tag)` wrapper calls `m_69330161(tag, false)`; the player refresh path calls the two-argument helper with `true`. All five exact helpers invoke the virtual `world.isChunkLoaded(x >> 4, z >> 4)` before fluid lookup, and their corresponding `ClientWorld` overrides return true as recorded in Correction 1. This changes only symbol precision and does not alter the dead-gate conclusion or the independent contextual-height finding.
