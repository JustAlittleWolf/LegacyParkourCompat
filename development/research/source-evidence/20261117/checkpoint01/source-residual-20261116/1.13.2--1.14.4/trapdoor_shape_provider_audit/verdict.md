# Independent audit: trapdoor collision-shape provider

## Verdict

**ACCEPT** the bounded provider-equality report at SHA-256 `6588841E916AB443BE029F85F765C11046893946F0D3B93C4EC8D83EFD4387AF`.

The 1.13.2 and 1.14.4 Feather ready markers identify the assigned versions and mapping. Their source-manifest digests match the on-disk manifests (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`). Every source hash cited in the report matches both the respective sidecar entry and the local source file.

## Claim checks

- The registered seven-key family resolves to `TrapdoorBlock` at both endpoints. The class schemas use the same five properties and the default-state values match.
- The six shape bounds and `getShape` branch logic match. In the 1.14.4 getter, the added `CollisionContext` argument is unused; output depends on `OPEN`, then `HALF` or `FACING` exactly as in 1.13.2. `POWERED` and `WATERLOGGED` do not enter the getter.
- The base `getCollisionShape` implementations check `hasCollision` and delegate to the state shape getter. The trapdoor registrations use the ordinary wood/iron block properties and do not call a no-collision property builder.
- The cited `neighborChanged` bodies both run only off the logical client, compare neighbor signal with `POWERED`, set `OPEN` to the signal when needed, and write `POWERED` to the signal. The new extra `movedByPiston` parameter is not read in this method.

These checks support shape equality for matching trapdoor states and support the report's bounded callback statement. The report correctly excludes complete block-query enumeration, collision resolution, other state producers/history, and pair freeze; this audit makes no such claims.

## Identity verification

The six Java files in the report were verified against their canonical source sidecars. The report's own hash was recomputed before review. No product sources, mixed backlog, wiki material, builds, or tests were used.
