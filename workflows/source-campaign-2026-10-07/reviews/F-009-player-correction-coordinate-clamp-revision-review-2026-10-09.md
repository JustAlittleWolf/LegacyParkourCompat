# Independent review of revised F-009: coordinate clamp scope

- Verdict: **ACCEPT** for the narrowed, conditional client-response claim.
- Review date: 2026-10-09.
- Pair: 1.19.2 → 1.19.3.
- Review boundary: this is a fresh review of the exact revised F-009 bytes and the bounded reachability request from the prior review. No mod implementation, wiki/MCPK, or mixed coordinator records were inspected. This is not a full-pair audit.

## Exact revised snapshot

- Source owner commit: `0f17dc31e6619d38752c862b9ec3a30c13b79f48`.
- Path: `workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-009-player-correction-coordinate-clamp.md`.
- Git blob: `fd6662d4d3eebe9671e9440cf67c27eb94d6bece`.
- Raw blob SHA-256: `26fca3cbdc84084458484c7cc305f684dc94cc43ccc4d77b0609a7fc296926e3`.
- Prior review: `f539b65862249af400c4ff15553323f864ddd3cf` rejected the earlier blob `21f79bf3b7283676cddedc3c1eb64f6793383c24` (raw SHA-256 `bfbc58498c6c969a75b1287b9e49a612f2e0fe94823df3e2845b5b6779fadf50`) for implying vanilla producer reachability. That rejection remains valid for the earlier bytes; this acceptance applies only to the revised snapshot.

## Exact source identity

The revised candidate retains the previously checked original Mojmap publications. Their manifest hashes were rechecked against the read-only cache:

- A, 1.19.2: `mojmap.sources.sha256` SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; `artifacts.sha256` SHA-256 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`.
- B, 1.19.3: `mojmap.sources.sha256` SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; `artifacts.sha256` SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`.

The source files cited by the finding and the added vanilla producer-gate evidence were rehashed and match those manifests:

- `ClientPacketListener.java`: A `98453fac8b16623d7b7f94f19199fa70c3b1d4885c464a2ab521a978a58ecbd3`; B `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`.
- `Entity.java`: A `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; B `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`.
- `ServerGamePacketListenerImpl.java`: A `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2`; B `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`.
- `ClientboundPlayerPositionPacket.java`: A `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0`; B `18f1e4f2ebc30510eefb53f139bb97402cf3cf4e1ba803f95d486e983a76e114`.
- `TeleportCommand.java`: both `3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a`.
- `Level.java`: A `2ff6b7ab66c73f0fa41136ab83fb8076742c6bde7c3af10dd6f3170d6d536539`; B `df465bcb6eb6ceaeb423485070e00737102abb3da064e44c440f69f3aede61c1`.

## Decision basis

The paired client source proves the narrowed claim. For an absolute correction (the X and Z relative-argument bits are absent), A's `ClientPacketListener.handleMovePlayer` calls `absMoveTo`; `Entity.absMoveTo(double,double,double)` clamps X/Z to `[-3.0E7, 3.0E7]` (A ranges: listener 563–628, entity 1202–1209). B's handler uses `setPos` directly, whose implementation writes position and bounding box without that clamp (B ranges: listener 592–667, entity 376–379). The packet codec reads and writes X/Y/Z as doubles, and its `handle` dispatches to `handleMovePlayer` without a coordinate-range check (both packet files, lines 18–52). Therefore, when a client receives a finite out-of-range absolute X or Z from an external/custom server, A and B produce different player position and bounding-box state consumed by subsequent movement.

The vanilla producer limit is now explicit and correct. In both versions, `TeleportCommand.performTeleport` rejects a target whose `BlockPos` fails `Level.isInSpawnableBounds` before the same-level player connection teleport call (command lines 257–285; A Level lines 155–164; B Level lines 158–167). Its horizontal world-bounds check requires each block coordinate to be at least `-30000000` and strictly less than `30000000`. The paired `ServerGamePacketListenerImpl.teleport` can preserve original arguments in the packet despite clamping its own player, but this helper does not establish a vanilla caller with out-of-range arguments. The revised finding makes no such claim.

I accept the source-proven client response only under the stated external/custom-server packet condition. This does not establish vanilla gameplay reachability, how often such packets occur, an actual trajectory, persistent server/client divergence, or runtime parity. No vehicle or damage behavior is involved.

## Next action and status boundary

Record this acceptance against the exact revised commit/path/blob above and preserve the earlier rejection event. The pair remains open and un-audited; this finding review does not mark pair completion. No implementation or runtime status is asserted. No source report or coordinator ledger was changed by this review.
