# Independent review: 1.8.9 Ender Chest ID 130 provider closure

Verdict: **ACCEPT within the stated source scope.**

Reviewed input report: .native-source/20261028/collision-next/dda-ender-chest-130-closure/report.md, SHA-256 16AE12055D4DCEAE4A3079B4FDE97B2A267916037890749E4B080BFD5D854A7D.

## Evidence identity

The canonical ready markers identify 1.8.9 / ornithe-feather and 26.2 / unobfuscated. Their embedded source-manifest digests match the canonical source manifests. I recomputed all 20 entries in sources.sha256; every file hash matched, and all 14 selected Java-file entries matched their corresponding canonical source-manifest rows.

## Claim review

- The 1.8.9 registration table maps ID 130 to ender_chest, and Blocks.ENDER_CHEST resolves that key. The constructor sets bounds to (1/16,0,1/16)–(15/16,14/16,15/16). A full-file search found no further setShape or updateShape override in EnderChestBlock.
- The metadata decoder applies Direction.byId(metadata) then maps vertical axes to north. byId uses abs(id % 6). For metadata 0–15 the resulting horizontal states match the report's table; all values remain within the old state's horizontal FACING domain. Default facing is north.
- Base old rayTrace calls updateShape and then reads instance bounds. The inherited updater is empty, so the per-query ray path does not alter this Ender Chest's fixed constructor bounds. This supports the bounded no-writer claim for this block only.
- The 26.2 registration is minecraft:ender_chest; the default namespace is applied to the ender_chest ID. Its states add WATERLOGGED, while getShape returns one static Block.column(14,0,14) shape regardless of state, position, level, or collision context. The centered 14/16 width and 14/16 height yield the same exact bounds as old. hasCollision defaults true, and the registration does not disable it.

## Scope

The report correctly limits its conclusion to this block's registry/state alias, collision provider geometry, and old instance-bounds read/write lifecycle. It makes no unsupported claim about full DDA traversal equivalence, camera reachability, or runtime parity.
