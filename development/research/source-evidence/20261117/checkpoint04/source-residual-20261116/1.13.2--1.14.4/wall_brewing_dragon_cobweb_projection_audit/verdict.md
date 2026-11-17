# Independent source audit verdict

**Verdict: ACCEPT.** Audited `wall_brewing_dragon_cobweb_projection/report.md`, SHA-256 `61dc8889df4fad9f5559b7042789651083bf4144953ba1abf0954765f26b4b38`. No corrections required within the assigned three-key, four-horizontal-direction scope.

## Evidence checked

- The endpoint source-manifest files hash to the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Every listed source file hash in the report was recomputed against the manifest and matched.
- The 1.13.2 registrations in `Block.java` resolve the named keys to `BrewingStandBlock`, `DragonEggBlock`, and `CobwebBlock`; the 1.14.4 registrations in `Blocks.java` do likewise. The bottle properties are exactly three Boolean properties, yielding 8 brewing-stand states; the egg and cobweb define no block-state properties, yielding one state each. Providers do not branch on state.
- 1.13.2 `WallBlock.shouldConnectTo` accepts the generic arm only for `FaceShape.SOLID` outside its exception set; its other arm accepts the stated pole shapes, with `MIDDLE_POLE` gated by `FenceGateBlock`. Each assigned old provider returns `UNDEFINED` for all horizontal faces, so both arms are false for all three keys.
- 1.14.4 wall placement and neighbor updates pass the opposite horizontal face into the wall predicate. The predicate is `!isExceptionForAttachment(block) && faceSolid || wall-tag-or-directional-fence-gate`; none of the keys is an attachment exception or fence gate. The exact extracted `walls.json` and `leaves.json` payload hashes match the report, and their value lists contain none of the three keys. Thus the tag and leaf gates cannot enable a bit.
- The target collision path honors `hasCollision`; brewing stand and dragon egg use their inset collision shapes. `Block.block` divides coordinates by 16. Each shape is inset on both horizontal axes, so its north/east/south/west face projection is empty and cannot be a full block face. Cobweb registration sets `noCollision`, so its collision shape is empty. These results agree with the reported false/false/false/false directional bits.

The report's bounded conclusion is supported. This verdict accepts only those keys, their state domains, and horizontal wall-bit projection for 1.13.2→1.14.4; it does not extend to other registrations, intermediate patch boundaries, pair coverage/freeze, implementation, or runtime parity.
