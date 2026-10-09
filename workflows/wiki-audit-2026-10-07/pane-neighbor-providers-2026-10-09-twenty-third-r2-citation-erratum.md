# Pane neighbor-provider slice 23 — citation/hash erratum r2

Snapshot ID: `wiki-pane-neighbor-providers-2026-10-09-twenty-third-r2-citation-erratum`

Status: metadata-only correction to the cited 1.9.4 `FlowerBlock.java` source identity. Independent re-review pending. This erratum preserves the original memo and review request unchanged and does not reopen the provider comparison.

## Immutable input and prior disposition

- Frozen candidate tree: `8f97dda72e0775c1e39990b087433b2e552d5564`.
- Original memo: `pane-neighbor-providers-2026-10-09-twenty-third.md`; Git blob `4de5f6f5821a9653e7251402f8ed9d5a3843b457`; raw SHA-256 `e9893ae2d0eb455378530c20cd5be3327d8b5eb974e81b4b9c2dcb933a0a8a6c`.
- The unchanged REQUEST CHANGES report is `pane-neighbor-providers-review-2026-10-09-twenty-third.md`, Git blob `cc94fa416e1f10c92ec709f8b048adb208304979`, at commit `def0033e467ca164f4357550631ded6da5ac8567`.

## Corrected source binding

The memo's 1.9.4 `FlowerBlock.java` row should use SHA-256 `c99f86acd562457fbb03f921f35429e948f7701a85078b74ac0c5b000b0eeda3`. I verified this exact file at `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/block/FlowerBlock.java`; its bytes match both the freshly computed digest and the 1.9.4 `ornithe-feather.sources.sha256` row `c99f86acd562457fbb03f921f35429e948f7701a85078b74ac0c5b000b0eeda3 *net/minecraft/block/FlowerBlock.java`.

The rejected value `4139a2fc5c274704d8cb861269bc2c62027ffb75f4e17b4aea85cb77a754ccf4` is the verified 1.8.9 `FlowerBlock.java` digest and matches its 1.8.9 source-manifest row. Only the 1.9.4 source-hash binding is corrected here. Registration, predicate, and pane-mask findings remain exactly those of the original memo; this erratum does not repeat or extend their comparison.

The original derived-JAR equivalence limitation remains unchanged. This correction alone does not accept the original memo; independent review must bind to this erratum and its exact bytes.
