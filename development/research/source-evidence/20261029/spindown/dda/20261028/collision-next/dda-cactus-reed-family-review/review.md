# Independent review: cactus and reeds provider closure

**Verdict: ACCEPT**, bounded to the source identities, AGE crosswalks, local collision providers, and ray/outline provider dimensions stated in the finding. This review does not establish DDA visitation, hit order, reachability, or player-trajectory parity.

## Reviewed inputs and identities

- Finding: `.native-source/20261028/collision-next/dda-cactus-reed-family-81-83/FINDING.md`
- Finding SHA-256: `B4E12E2B2E2EFDE8D34017B61AD17EDB9EC060BD6A9D1F8963E7AD2EA7E57D42`
- Source JSON: `.native-source/20261028/collision-next/dda-cactus-reed-family-81-83/source-hashes.json`
- Source JSON SHA-256: `14CF99E5212B512C355D5F0D668BFB582C641B64ED4B6BA0A7573675D1429F2E`

The listed identity hashes match the canonical source metadata: 1.8.9 Feather ready marker `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95`, provenance `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185`, and sources manifest `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`; 26.2 ready marker `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`, provenance `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`, and sources manifest `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`.

All ten selected Java file hashes were independently recomputed against the canonical files and matched both the source JSON entries and the release source-manifest rows (10/10, zero mismatches).

## Verified claims

- Historical `Block.java:1012,1014` registers IDs 81 and 83 as cactus and reeds. Both historical classes define AGE 0–15 and encode/decode it directly. 26.2 `BlockStateData` lines 1296–1311 and 1328–1343 map the complete age ranges to `cactus` and `sugar_cane` respectively; the current key declarations and registrations are explicit in `BlockItemIds` and `Blocks`.
- Historical cactus collision uses the world-positioned box inset 1/16 on X/Z and ends at Y=15/16. Current `CactusBlock` returns `Block.column(14, 0, 15)` for collision. The source-defined box dimensions agree for every AGE. Cactus outline geometry is separately inset in X/Z and reaches Y=1 historically; current outline uses `Block.column(14, 0, 16)`, so it differs from the inherited historical unit-box ray bounds.
- Historical reeds collision returns null. Current sugar cane registration uses `.noCollision()`, whose base collision provider returns an empty shape. The historical constructor sets ray bounds to X/Z 1/8–7/8 and Y=0–1; current outline uses `Block.column(12,0,16)`, with matching local dimensions. These are ray/outline facts, not movement-collision geometry.
- The old Block constructor initializes the mutable bounds to a unit cube and its default `updateShape` is empty; default `rayTrace` calls `updateShape` before clipping. Neither cactus nor sugar cane overrides this provider path in the selected sources. The finding correctly keeps those inherited bounds distinct from their collision/outline overrides.
- The old cactus `onEntityCollision` damage call and current cactus `entityInside` damage call are separate from the collision-shape methods. The finding correctly excludes them from its movement-collision conclusion.

The source references and scope limits in the finding match the inspected code. No material error found.
