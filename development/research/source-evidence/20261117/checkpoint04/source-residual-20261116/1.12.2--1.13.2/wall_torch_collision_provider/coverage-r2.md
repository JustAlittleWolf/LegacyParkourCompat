# Wall-torch collision provider correction r2: registered fixer path

## Binding

This narrow correction is bound to the unchanged source report `wall_torch_collision_provider/report.md` (SHA-256 `515e5e0e99b5f9ffef23cab5acb8d3979cbe32fc4de14102c7daf7334ffdcd3d`) and its initial independent audit `wall_torch_collision_provider_audit/verdict.md` (SHA-256 `1781bfcc42180161df4c1be29e6df3d4bc1a174d08019fd128412f0188b91cb4`). The audit accepted the bounded provider/key/state and collision conclusions, but requested correction of the claim that no call site for `C_80754635.m_11299400` exists.

Both added 1.13.2 source files were rehashed from canonical bytes and matched their entries in the ready-bound source manifest (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`):

| Source | SHA-256 |
|---|---|
| `net/minecraft/unmapped/C_15874933.java` | `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71` |
| `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |

## Corrected migration-source disposition

The original report's sentence that no call site for `C_80754635.m_11299400` exists in the inspected source tree is withdrawn. `C_15874933.makeRule` applies `C_80754635.m_11299400` to the remainder of the `TypeReferences.BLOCK_STATE` input (`C_15874933.java:15–20`). `DataFixes` registers `new C_15874933(schema37, false)` after schema 1450 (`DataFixes.java:227–229`). The four horizontal `torch`→`wall_torch` mappings in the already audited table are therefore used by a registered block-state data-fixer rule.

This establishes the registered in-tree fixer path only. Whether every external/custom importer or a particular external loading path invokes the target data fixer remains outside these source claims. No geometry/provider comparison is repeated here: the initial audit's accepted collision and `FaceShape` conclusions remain as stated for the bounded four horizontal states.
