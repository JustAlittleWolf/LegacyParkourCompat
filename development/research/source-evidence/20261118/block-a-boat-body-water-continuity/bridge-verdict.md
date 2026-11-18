# PARTIAL

Reviewed immutable source commit `2b8c0a4a367c477fe1f8d7701ddfb55e125953f0`.

Bound claim: finding SHA-256 `34d711e4dcd0c124123943932d9f99fa487a5beb9aa1d598830f8b5fde2dc409`; source-matrix SHA-256 `0e4619649efc8731807e90e3d53125018b07a7e84e1a83ebf49810a2b6883b61` (UTF-8 with LF). The listed source-file SHA-256 values all match the canonical files in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

The bridge bodies support the claimed unconditional clear across the inspected files: the 1.10–1.13 variants test `getMount() instanceof BoatEntity` and set the body-water flag false before the fluid scan; 1.14 Yarn uses `getVehicle() instanceof BoatEntity`; 1.14.4 onward uses `getVehicle() instanceof Boat`. The predicate has the same relevant effect as the stored flag/member evolves. The update is reached through the inherited Entity tick/base-tick path for the local player; the surrounding wrapper changes (including the 1.14 water-state wrapper and the 1.16+ fluid-pushing wrapper) still call the unconditional gate. This agrees with the accepted early 1.9–1.9.4 and late 1.18–1.19.2 evidence. The 1.19.3 underwater exception is outside this bridge audit.

Exact per-release artifact/mapping provenance remains unverified for markerless source directories 1.10, 1.10.1, 1.12, 1.12.1 and 1.14–1.14.3. Their source files and hashes are present, but the audit does not infer provenance from directory names. Consequently the code behavior is confirmed in those pinned files, while the exact-version identity for those rows is not independently established; the full exact-version bridge claim remains partial.

## Provenance rebind: ACCEPT (bridge source-identity gate)

Rebound against corrected immutable source commit `a7b251e473913cb872ef73a0823618f182dac090`. Finding SHA-256 `ee3297cdd8b12006097b3be0b40f40b7b0e5e7cbc4b6bd36d125e5faeda1069a`; matrix SHA-256 `b4a0a25b94161d8523a1a2c79b32f6e08e3a58be438596a68d0b1611112dae71` (UTF-8 with LF).

For all eight formerly markerless rows, the hidden `.decompile-provenance.json` exists under the exact mapping root. Independently checked each provenance JSON SHA-256 against the matrix; each JSON's exact release metadata, client artifact, mapping metadata/artifact input pins identify the listed release/build and match the matrix pin values. For `net/minecraft/entity/Entity.java`, every JSON source digest matches both the matrix and SHA-256 of the canonical file. Thus the version/source provenance gap recorded in the original PARTIAL is closed for 1.10, 1.10.1, 1.12, 1.12.1 and 1.14–1.14.3; the unconditional vanilla Boat-passenger body-water clear remains accepted for this bridge scope. This rebind covers direct body-water contact and its predicate only.
