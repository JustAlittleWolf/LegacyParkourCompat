# Independent blind source audit verdict

## Audited input identity

- Frozen report: `c-early-189-19-sprint-flight-input/report.md`, SHA-256 `bf5401df3f098be3e2072e7f140d4587503c7782cdc7a341c3a14ebf81dc617b` (matches the assigned immutable digest).
- Pair and mapping: exact `1.8.9 → 1.9`, local canonical `ornithe-feather` Feather sources. The 1.8.9 ready marker names Feather gen2 `1.8.9+build.2` merged-v2 plus Tiny and client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`. Its ready marker, adjacent provenance, and source-manifest hashes are respectively `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`, `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, and `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- The 1.9 `.decompile-provenance.json` SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. It records the exact 1.9 metadata SHA-256 `8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688`, client SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, and Feather gen2 `1.9+build.2` merged-v2 SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`.
- Recomputed SHA-256 hashes for every audited source file match the 1.8.9 source manifest and 1.9 provenance source map. In older/newer order: `LocalClientPlayerEntity.java` `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` / `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`; `KeyboardInput.java` `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` / `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `Entity.java` `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` / `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`; `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` / `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` / `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`.

## Claim verdicts

### Local sprint timeout and double-tap window — underlying difference accepted; one sentence needs correction

The paired source supports initialization to `600` on sprint-start and `0` on stop in 1.8.9 (`LocalClientPlayerEntity.setSprinting`, lines 349–352), followed by a positive-only per-`mobTick` decrement and stop at zero (`mobTick`, 488–494). The normal tick path reaches `mobTick`: local-player `tick` calls `super.tick` (1.8.9 lines 105–107; 1.9 lines 141–143), and `LivingEntity.tick` invokes `mobTick` (1.8.9 line 1296; 1.9 line 1538). In 1.9, `setSprinting` assigns zero on either transition (402–405), while `mobTick` increments the timer unconditionally (600–604). The complete 1.9 `net/minecraft` source search finds no timer read; the separate double-tap timer still decrements and initializes to `7` (602–604, 658–671), with the stated item-use clear at 646–650.

The frozen report's phrase “no `sprintTimer` read remains in either release's `net/minecraft` source tree” is false as written: 1.8.9 reads the timer at lines 489–491, as the report itself cites. Narrow it to “no `sprintTimer` read remains in the 1.9 `net/minecraft` source tree.” With that wording correction, the movement consequence that the 1.8.9 local timeout expires after the 600-count path and this expiry path is absent in 1.9 is supported. This is a source finding, not a runtime claim or exact intermediate cutover.

### Ability-flight fall-distance reset — accepted

`PlayerEntity.moveRelative` in 1.8.9 is gated by `abilities.flying && vehicle == null` (1279–1292); after `super.moveRelative`, it damps saved vertical velocity by `0.6` and restores `speedInAir`, without resetting fall distance or flag 7. In 1.9 the gate is `abilities.flying && !isRiding()` (1366–1381); after the same call, damping and speed restoration, it assigns `fallDistance = 0.0F` and clears flag 7 (1374–1378). `LivingEntity.isFallFlying()` returns flag 7 in 1.9 (2038–2040), confirming the flag meaning. The described branch condition and operation order are accurate.

### Flying sneak horizontal input rescaling — accepted

Both `KeyboardInput.tick` methods scale the float axes with `(float)(axis * 0.3)` when sneaking (1.8.9 lines 32–37; 1.9 lines 44–49); `Input` declares both axes as `float`. Only 1.9's local-player flying-camera branch divides each axis by unsuffixed `0.3` and casts each quotient back to float before subtracting the unchanged vertical fly-speed amount (710–719). It follows `input.tick()` and item-use scaling by `0.2F` (645–650). Both `Entity.updateVelocity` methods then perform the described float squared-length threshold, square-root/minimum-one normalization, scaling and yaw rotation (1.8.9 lines 844–859; 1.9 lines 943–958). The operation order, types, gate and stated horizontal-input consequence are supported.

## Bounds

These verdicts cover only the three named source operations. They do not establish full-pair movement inventory, exact intermediate patch boundaries, runtime parity, or server-side historical execution. No product source, wiki inventory, Git history, network, generated sources, build, tests, or game launch were used.
