# Banner shape provider audit verdict

## Verdict

**PASS, bounded to the Banner provider family comparison.** The audited report is bound to SHA-256 `175978113618cf0541bd65e46f4211d628469d5cda6a5eab0a8d24a87aa35a38`. Its claims about collision and outline provider behavior, relevant state inputs, geometry, and normal player movement reachability are supported by the pinned exact sources. It correctly leaves the exact old registry-key to new dye-specific-key relationship unresolved.

## Evidence checked

- Ready markers identify 1.12.2 and 1.13.2 Ornithe Feather Gen2 build 2. The markers' declared source-manifest digests match the actual manifest files: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Source hashes in those manifests match the cited files: 1.12.2 `BannerBlock.java` `cd6793dcf6344fe613e1245a299424bd5bd0f4a1e34f3b4ca72f774d6ab5192d` and `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`; 1.13.2 `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`, `StandingBannerBlock.java` `bc8a64d47d7ed894288c7923f7e6feb1c9c1a0b58ad961c194fc04dbf7793c6d`, and `WallBannerBlock.java` `70d1fb6b45df1bd86b6a7276cab817472c2c974cad31cb7ac041da3224f04296`.
- In 1.12.2 the banner base collision provider returns the empty sentinel; standing outline geometry is `(0.25, 0, 0.25)–(0.75, 1, 0.75)` without reading rotation; wall geometry is selected by `FACING` with the reported 0.78125 height and 0.125 thickness. In 1.13.2, all registered banner instances are constructed with `noCollision()`, and `Block.getCollisionShape` therefore returns the empty voxel shape. Standing shape uses the equivalent 4/16-to-12/16 bounds without reading rotation. Wall shape selects the matching facing boxes with 12.5/16 height and 2/16 thickness. No mismatch was found in the paired provider families.
- The player reachability chain is valid: player entities inherit through `LivingEntity` to `Entity`; `Entity.move` queries world block collisions; those queries dispatch to the cited block collision providers and omit empty results. The report correctly distinguishes the outline shape from the empty collision contribution.
- The crosswalk digest is exactly `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`. It records `minecraft:standing_banner` and `minecraft:wall_banner` as `UNMATCHED_ALIAS_UNRESOLVED`, while 1.13.2 has dye-specific standing and wall registrations. This is the appropriate open dependency: pairing by mounting subtype supports the bounded geometry comparison, but the evidence does not establish an exact old-key alias or dye expansion mapping.

## Boundaries

This verdict accepts only the packet's bounded provider comparison and its cited reachability chain. It does not close the registry alias dependency, other provider/context dependencies, complete pair coverage, pair freeze, or runtime parity.
