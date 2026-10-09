# Independent source review: pane-provider slice 35, Stone Stairs

## Scope and verdict

Reviewed `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-fifth.md` at frozen memo commit `58fc0b6c8a94045d3bf9bd708908573da6fd5acf`, parent `60f70ded62ebed9eec375a559414213506801bca`; Git blob `3bf654a6f4405e4e86df4bfe1f36f555c09498f2`, raw SHA-256 `f663cf9e0050b7251791aabc60b1874bf988ff35ac7425a5d2b93696b1763531`.

**Verdict: REQUEST CORRECTION, bounded to the base-block identity wording.** The reported false/false predicate result and empty east-neighbor mask are supported and may be retained. The memo’s phrase “stone-brick base block” should identify the registry binding as cobblestone ID 4, while noting that the local object has `setKey("stonebrick")`.

## Source bindings and evidence

Both ready records identify `feather-r1-2026-10-07`, report `ready`, and match the memo’s source-manifest hashes: 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. I recomputed all seven cited source-file hashes; each matches its source-manifest entry. The verification records report zero source differences and no raw-input differences. Original derived-JAR equivalence remains unproven; regenerated JARs do not establish that the recorded differences are metadata-only.

In both registry initializers, local `block` is constructed as `new Block(Material.STONE)`, assigned key `stonebrick`, and registered at numeric ID 4 under registry name `cobblestone`. ID 67 constructs `new StairsBlock(block.defaultState())`; therefore the memo’s “stone-brick base block” wording blurs the registry name and the object key. The exact provider input is the default state of the ID 4 cobblestone registration with that key.

That naming correction does not alter the bounded pane result. The old and new `StairsBlock` implementations return false from their respective solid-render/cube predicates. In 1.8.9 the base constructor caches `opaqueCube` from the overridden false `isSolidRender()`; in 1.9.4 the pane predicate dispatches `defaultState().isCube()` to the state-taking false override. The stair defaults are north-facing, bottom-half, straight-shape, but those predicate overrides do not read them. Both pane resolvers check the four cardinal directions; stairs are not on the explicit pane/glass allowlist. An east-only Stone Stairs neighbor therefore yields no E arm in either version; no predicate or mask delta.

| Version | Cited source SHA-256 |
|---|---|
| 1.8.9 `Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 `PaneBlock.java` | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.8.9 `StairsBlock.java` | `fadc964480227f6ffd5756471212b18d0172bffbcd0866ee0c278454ab079409` |
| 1.9.4 `Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 `PaneBlock.java` | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 `StairsBlock.java` | `ee143aced2a6902563cddbae77e78d1d1a512244f278987684ebe5bcacba37cb` |
| 1.9.4 `StateDefinition.java` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

This does not assess stair placement, shape recomputation, collision, or movement. The provider census, reachable-mask closure, and original derived-JAR equivalence remain open.
