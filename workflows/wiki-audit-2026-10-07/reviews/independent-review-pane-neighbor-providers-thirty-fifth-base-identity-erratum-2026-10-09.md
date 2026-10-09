# Independent review: pane-provider slice 35 base-block identity erratum

Verdict: ACCEPT

## Reviewed artifact identity

- Erratum commit: `574a1b3038a77f3afeb8039a6b9830d59ef8a4d9`
- Erratum path: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-fifth-base-identity-erratum.md`
- Erratum Git blob: `b943a036791642e76d127497042e4b616f21ba11`
- Erratum raw SHA-256: `fded0e8260fd29bba8efe8a8ae388c7abdecbebc6e7237e8dfbd4e3e0c6a47b2`

## Original memo identity

- Commit: `58fc0b6c8a94045d3bf9bd708908573da6fd5acf`
- Path: `workflows/wiki-audit-2026-10-07/pane-neighbor-providers-2026-10-09-thirty-fifth.md`
- Git blob: `3bf654a6f4405e4e86df4bfe1f36f555c09498f2`
- Raw SHA-256: `f663cf9e0050b7251791aabc60b1874bf988ff35ac7425a5d2b93696b1763531`

## Source evidence

The frozen memo describes the ID 4 `block` as the “registered stone-brick base block.” The erratum corrects this identity wording: the registry initializer creates `new Block(Material.STONE)`, sets its key to `stonebrick`, and registers that same object under registry name `cobblestone`, ID 4. The following `stone_stairs`, ID 67, registration constructs `new StairsBlock(block.defaultState())`.

- 1.8.9 ready source, `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/block/Block.java`, SHA-256 `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`: lines 830–836 show the base object, key, and ID 4 registration; line 967 shows the ID 67 stair registration.
- 1.9.4 ready source, `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/block/Block.java`, SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`: lines 722–728 show the base object, key, and ID 4 registration; line 863 shows the ID 67 stair registration.

Both source files support the correction. The review is limited to the base-block identity wording and these cited source lines and hashes; it does not assess the memo’s predicate or mask findings, implementation, or broader provider coverage.
