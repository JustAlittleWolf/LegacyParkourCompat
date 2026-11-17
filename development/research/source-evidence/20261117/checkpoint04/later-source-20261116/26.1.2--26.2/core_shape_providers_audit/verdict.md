# Independent audit: selected block shape providers

**Verdict: ACCEPT** — bounded source claims supported. This is not a pair-wide source audit or movement-parity result.

## Immutable input

- Report: `build/later-source-20261116/26.1.2--26.2/core_shape_providers/coverage.md`
- SHA-256: `E18D57229563B8D1C40D5CAE28F4CBA04158D31AEA68EB5495DC6EC999E1F629`

## Source identity and hashes

Verified both endpoint ready markers identify the expected version and `unobfuscated` mapping. Provenance identifies the same version and `namespace=unobfuscated`. The SHA-256 of each `unobfuscated.sources.sha256` and `artifacts.sha256` matches the corresponding ready marker binding. Manifest file counts match 6,882 for 26.1.2 and 7,055 for 26.2. Actual sidecars are at each `decompiled_minecraft/<version>/` root.

For every listed Java path, the local file SHA-256 matches the endpoint source-manifest row and the report table: `SnowLayerBlock.java`, `CarpetBlock.java`, `WoolCarpetBlock.java`, `ScaffoldingBlock.java`, `SlimeBlock.java`, `HoneyBlock.java`, and `Blocks.java`.

## Audit findings

- The snow, carpet, wool-carpet, and scaffolding source files are byte-identical between endpoints. Their reported state defaults, selected shape methods, carpet survival predicate, scaffolding collision branches and distance provider match the inspected source. `Blocks` registrations use the cited providers; the moss carpet and scaffolding property excerpts match, aside from the registry key representation. The wool-carpet color registration form changes to `ColorCollection` in 26.2 but still constructs `WoolCarpetBlock`.
- `HoneyBlock.java` differs overall, but its cited `SHAPE` constant and `getCollisionShape` implementation are the same. The `HONEY_BLOCK` registration retains the cited speed/jump factors and `.noOcclusion()` properties; only the registry key representation changes in the excerpt.
- `SlimeBlock` has no direct shape-provider override in the cited source. Its registration preserves friction and no-occlusion, while 26.2 adds `.bounceRestitution(1.0F)`. The report states that this response change is outside scope and reaches no restitution or movement conclusion.

The checked claims and their explicit limits agree with the exact source. Snow tag membership, ordinary state/data reachability, `MossyCarpetBlock`, Slime contact/restitution behavior, Honey contact/slide behavior, other providers, inherited collision dispatch, full-pair coverage, and runtime parity remain unexamined as stated.

No correction is required within this bounded report.
