# Discovery: 1.10.2 to 1.11.2

- Status: partial
- Scope: client player movement and directly reachable movement state; A=1.10.2, B=1.11.2.
- Repository revision/start: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26.
- Namespace alignment: Ornithe Feather, A `1.10.2+build.2`, B `1.11.2+build.2`.
- A generation provenance: owner log `decompile-1.10.2-feather.log`, exact release, successful completion, 1 class/33 members access fixed; A was not regenerated.
- B command: `gradlew.bat decompileMinecraft --versions=1.11.2 --mappings=feather --no-daemon --gradle-user-home <worktree>/.gradle-home-movement-discovery`; success in 3m7s, 1,921 Java files. Full log not persisted.
- Toolchain: Gradle 9.7.1; Temurin 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; heap 4G.

## Artifact manifest

### A — 1.10.2

- Source: `../../../../decompiled_minecraft/1.10.2/ornithe-feather/`.
- Client jar: `../../../../build/minecraft-decompile-cache/1.10.2/client.jar`; SHA-1 `dc8e75ac7274ff6af462b0dcec43c307de668e40`; SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`.
- Mapping `net.ornithemc:feather-gen2:1.10.2+build.2`; Tiny SHA-256 `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.
- Actual merged-v2 mapping jar SHA-256 `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; actual remapped client jar SHA-256 `c42fe4366482d183f83ff62be5abfefead9d3350e7c9dd9243f8ac1a5b59ed90`.
- Owner manifest swapped these mapping/remapped hash assignments and reported `11d5e5…` for remapped jar. Hashes were verified at actual artifact paths; `11d5e5…` was not found. Discrepancy retained.

### B — 1.11.2

- Source: `../../../../decompiled_minecraft/1.11.2/ornithe-feather/` (1,921 Java files).
- Client jar SHA-1 `db5aa600f0b0bf508aaf579509b345c4e34087be`; SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- Version metadata SHA-256 `c98508dfc20ed365e6666c8f44879582df033bbde0e3499782381130f3dbaa1c`.
- Tiny mapping SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`; merged-v2 mapping jar SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`.
- Remapped jar SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Remapper warned of invalid access and repaired 1 class/34 members. Full log/target list unavailable; relevant sources inspected and selected affected movement methods verified with `javap -c -p`.

## Coverage ledger

- S1 input/tick ordering — compared, no delta in inspected Input/KeyboardInput/local tick/sprint gate paths; input source files byte-identical. Elytra null-check/API change did not establish movement delta.
- S2 player state/gates — partial, F005; remaining effects/equipment/enchantment closure open.
- S3 living movement — inspected jump impulse, relative movement and ground/air/water/lava/fall-flight formulas unchanged; B typed `MoverType.SELF` enters changed Entity.move (F001).
- S4 entity collision — partial, F001–F003; full collision-helper closure open.
- S5 blocks/fluids — partial, F004; reviewed ice/slime constants and snow collision shape formulas match. Support/rail/fluid closure open. Shulker box is new in B and modern-only for A scope.
- S6 effects/equipment/attributes — in progress; default step height 0.6F both sides; modifier/effect/tag closure open.
- S7 external influences — partial, F002–F004; knockback, explosions, incoming correction and all caller closure open.

## Finding index

F001 sneak-edge support probe changes from 1.0 to stepHeight; F002 piston mover type bypasses B sneak-edge guard; F003 B accumulates/clamps piston movement per world tick; F004 farmland conversion repositions intersecting entities to dirt top; F005 player jump and movement-distance exhaustion costs differ, with server-authoritative accumulation/synchronization.

## Limits and handoff

Source audit only; no build, tests, gameplay validation, trajectories or implementation changes. First changed release unknown within (1.10.2,1.11.2]. All seven stages have recorded status; dependency closure remains open. Generated source and local Gradle cache are not committed.
