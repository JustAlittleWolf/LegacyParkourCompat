# Independent review: 1.8.9 button provider and alias closure

## Verdict

**ACCEPT within the stated alias, state-decoding, and movement-provider scope.** The report correctly keeps old ray-picking bounds separate from movement collision and makes no DDA traversal or runtime-parity claim.

Reviewed input:

- `.native-source/20261028/collision-next/dda-button-family-77-143/report.md`, SHA-256 `1147EE5E3131988DA48A8262C2413F18A5E53627034E324F3549AB14097F9530`.
- `.native-source/20261028/collision-next/dda-button-family-77-143/sources.sha256`, SHA-256 `32D83AB62AC2E6C1A84EEF4903FBF61D9CE3BFEBB668B02DAC9A41EB54972A15`.

All ledger file hashes matched their referenced canonical files. The 1.8.9 and 26.2 ready marker and source-manifest identities match the report, and each selected Java hash also matches its canonical source-manifest entry.

## Claim checks

- The 1.8.9 registry assigns ID 77 to `stone_button` and ID 143 to `wooden_button`. Both subclasses share `ButtonBlock`'s `FACING` and `POWERED` state, default NORTH/unpowered. Metadata uses the low three bits for facing and bit 3 for powered; values 5–7 all decode to UP for each power bit. No invalid metadata value appears in the 0–15 domain.
- The 26.2 registry maps the historical stone button to `stone_button` and the generic historical wooden button to `oak_button`. `BlockStateData` rows preserve powered/facing values and translate old DOWN/UP to current ceiling/floor attachment with NORTH facing. The source does not justify mapping the old generic wooden button to another modern wood species.
- The old movement collision method returns null in every button state. Current button registrations use the no-collision property, and current `BlockBehaviour.getCollisionShape` returns an empty shape when collision is disabled. The empty movement-provider conclusion is supported for the mapped aliases.
- Old `updateShape` mutates shared bounds from state; `Block.rayTrace` calls it before intersecting those bounds. The report correctly treats this as ray-picking behavior, not evidence for the empty movement collision provider. Current outline shapes likewise do not change the movement-collision finding.

## Source identity

The source ledger's listed digests were rehashed. Relevant inputs include 1.8.9 `ButtonBlock.java`, `StoneButtonBlock.java`, `WoodenButtonBlock.java`, `Blocks.java`, `Block.java`, and 26.2 `ButtonBlock.java`, `Blocks.java`, `Block.java`, `BlockBehaviour.java`, `BlockStateData.java`, `BlockItemIds.java`, and `Shapes.java`. The ledger itself binds both release markers, provenance files, and source manifests.

No product code, build, tests, game, or runtime was used in this review.
