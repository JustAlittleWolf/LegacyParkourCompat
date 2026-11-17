# Independent audit: Slime bounce r3 resource crosswalk

**Verdict: ACCEPT** — r3 is a narrow resource-evidence revision to the previously accepted Slime bounce r2 source report.

## Immutable identities

- Reviewed report: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_revision_r3/coverage-r3.md`
- Reviewed report SHA-256: `4681e1e4a918b0ed1f69dbfb2748828c817808558142ef8988db9bd7132016be`
- Prior accepted source report: r2 SHA-256 `d6fd005ad2fd60c3d10645036c5286063eb645808bba560e776301004390453f`
- Prior source audit: `build/later-source-20261116/26.1.2--26.2/slime_bounce_transition_r2_audit/verdict-r2.md`, SHA-256 `aa55c0770a4ac1ae80d0f2764028946035d03d8e3d7743e7e9b36b30a88c505f`
- Resource input reference: `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_suppresses_bounce_tag/input-reference.json`, SHA-256 `e8ec85f81c3f0b187bdde06344e41b8be7b41cc7aee3745753f547d8248eaf0f`
- Independent resource audit: `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_suppresses_bounce_tag/audit/verdict.md`, SHA-256 `1a755a6a5ee67e7b6879d84e55ecc93e4c46c9c056c2f8f198c64deab0dd2d89`

## R3 audit findings

The r3 report retains the accepted r2 source comparison, call path, and limitations, and adds the pinned `SUPPRESSES_BOUNCE` tag crosswalk. The added claim is supported:

- `BlockTags.SUPPRESSES_BOUNCE` is declared as `create("suppresses_bounce")`; `create` uses `Identifier.withDefaultNamespace`, which constructs the `minecraft` namespace. Thus its archive entry is `data/minecraft/tags/block/suppresses_bounce.json`.
- Local `BlockTags.java` and `Identifier.java` SHA-256 values match both the r3 table and the 26.2 `unobfuscated.sources.sha256` entries: `9834ecbe2facd79cf3d9fc79babd452ed0e000684029b2bb5e9f90aa4f51529e` and `053f2c75971305a75b350e529dbef832a5175cd388991831d4ba0c6e340faac6` respectively.
- The referenced resource input and its independent ACCEPT audit bind the exact pinned 26.2 client artifact and source tag crosswalk. The sole tag value is `minecraft:honey_block`; `minecraft:slime_block` is absent. The extracted 49-byte payload SHA-256 is `a477a87ac4bcb97971cb0b445f4cc9b6b8e02cd31ba3d01bc842b17a6a8477a8`, matching the local output. This supports the bounded statement that the tag gate does not filter SlimeBlock in that pinned vanilla archive payload.
- A diff against r2 shows only the resource crosswalk and the corresponding scope/limitation wording changes; the prior bounce math, client movement call path, source identities, and existing cited file table are retained. The resource audit also records that the provenance sidecar does not directly record the JAR digest or cache path.

The source delta remains conditional on downward collision with a SlimeBlock: the pinned archive tag does not suppress SlimeBlock, leaving the impact-speed threshold as the remaining newly introduced gate. This audit does **not** establish that an ordinary player trajectory reaches an impact below effective gravity. Datapack overrides, reload state, effective runtime tag membership, threshold reachability/frequency, and pair-wide coverage remain open. No runtime or implementation conclusion is made.

