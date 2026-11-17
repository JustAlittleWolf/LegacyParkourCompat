# Independent audit: wall-torch fixer-path correction r2

## Verdict

**ACCEPT** for the narrow correction in `wall_torch_collision_provider/coverage-r2.md` (SHA-256 `4d55291f59f05cef9830eaf032efde375aabc82e41fc2f7aea39700411742dbd`). It binds the unchanged original report SHA-256 `515e5e0e99b5f9ffef23cab5acb8d3979cbe32fc4de14102c7daf7334ffdcd3d` and the initial audit verdict SHA-256 `1781bfcc42180161df4c1be29e6df3d4bc1a174d08019fd128412f0188b91cb4`; both values match current bytes.

## Correction verified

- `C_15874933.makeRule` (`C_15874933.java:15–20`) applies `C_80754635.m_11299400` to the remainder of `TypeReferences.BLOCK_STATE`.
- `DataFixes` adds schema 1450, then registers `new C_15874933(schema37, false)` (`DataFixes.java:227–229`). This confirms the in-tree registered fixer path.
- The added sources match their canonical bytes and manifest entries for ready identity 1.13.2 (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`):
  - `net/minecraft/unmapped/C_15874933.java` — `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`.
  - `net/minecraft/util/datafix/DataFixes.java` — `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`.
- The r2 note retracts the erroneous no-call-site sentence and does not change the original provider, state-mapping, or collision claims. It correctly limits the new evidence to the registered in-tree fixer; invocation by every external or custom importer remains unproven.

No provider geometry was re-compared. The original verdict’s bounded collision and `FaceShape` dispositions remain accepted for the four horizontal states; no outline equality, migration-path universality, movement trajectory, or pair-completion claim is made.
