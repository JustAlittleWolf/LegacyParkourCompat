# Independent source audit: final liquid scan comparison revision

**Verdict: ACCEPT**

The assigned comparison `.native-source/20261028/liquid-scan-upper-bound-closure/comparison-revised-2.md` has SHA-256 `32C560CDE176ACF935EE0D46A604005B6DE2DDA8A5279ED666554B800DF59B82`, matching the requested immutable revision.

## Final anchor checks

The added source spans are correct and now include the complete behavior they support:

- 1.9.4 `World.containsMaterial` is `World.java:1604–1626`; the range includes the early-match release/true return and the exhausted-scan release/false return at lines 1624–1625.
- The full `isAreaLoaded(int,...)` helper bodies are 1.8.9 `World.java:205–224`, 1.9 `:210–229`, and 1.9.4 `:216–235`. Each includes the `maxY >= 0 && minY < 256` gate, inclusive shifted chunk loops, and the out-of-range `return false` branch.
- The prior call-chain correction remains accurate: `tick()` calls `baseTick()` and `baseTick()` calls `checkWaterCollisions()` at the line locations stated in the revised comparison.
- The 1.9.4 movement caller location is correctly `LivingEntity.java:1302–1306`.

The previously audited scan behavior remains supported: 1.8.9 uses `floor(max + 1.0)` for exclusive upper bounds in both scans; 1.9 and 1.9.4 use `ceil(max)`, with `floor(min)` and half-open loops retained. The integral-edge voxel consequence, state/material access path, water-body surface check using the calculated Y upper bound, loaded-area dependency, and material-drag/velocity logic agree with the canonical source. Player inheritance, lava and water caller paths, and boat suppression are accurately described. The comparison does not infer runtime parity or claim an exact patch boundary.

## Source and provenance identities

All listed `World.java`, `Entity.java`, `LivingEntity.java`, and `PlayerEntity.java` full-file SHA-256 hashes were checked against the canonical files. Ordered by those four files:

- **1.8.9:** `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`; `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- **1.9:** `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`; `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`; `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`; `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`.
- **1.9.4:** `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `d658a0d95452d12bb7e347bfd802240eeeaf7f938e10dcd43e2640434387f85`.

The exact inspected metadata record hashes remain: 1.8.9 provenance `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, ready `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`, verification `a4206eee9ffb45a6f54f93371c8275b7a6c37cf9ab1ca93d5fa0f5d889881620`, and source manifest `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1.9 literal hidden provenance `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; 1.9.4 provenance `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`, ready `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`, verification `9ef993b06552037873604f912cce078845241b6e6f3b9799a83795abd1adf688`, and source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The source manifests/records contain the matching source hashes above; 1.8.9 and 1.9.4 verification metadata reports zero source-file differences.

No 1.9.1, 1.9.2, or 1.9.3 source was inspected. Those patches and the exact introduction point remain explicitly open. No product code, tests, build, game, or runtime was inspected or run.
