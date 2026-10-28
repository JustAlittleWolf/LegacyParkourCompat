# Independent product re-review: 1.20.1–1.20.2 passenger placement

**Verdict: ACCEPT within the audited scope.** Reviewed final product tip `ea0d5a5e7bc460e600374401c387c6845a499965` (fix `efded0179b17946bfea34c0aa357574740335071`, merged with main `9536188c`).

Bound source inputs:

- Passenger-placement operation report `.native-source/20261028/rider-position-full-operation-closure/report.md`, SHA-256 `21c67eb18a77a59f4e1419c570a58a579b8d7f5425575ea62982da256a3fd235`.
- Full writer coverage audit `.native-source/20261028/frontier-c/1201-1202-all-passenger-writers-closure/coverage-audit.md`, SHA-256 `a1330651f1398ca12a799c7b4ed70d0278dfd708dff1caa64a0e9283d6adb781`.
- Candidate review `.native-source/20261028/rider-position-candidate-review/review.md`, SHA-256 `9fe9c234df044f01b5ea420bf93aec14faa8475061c8976bd186f64cbd49c323`.
- 1.20→1.20.1 movement alias report `.native-source/20261028/frontier-c/1200-1201-full-inventory/report.md`, SHA-256 `c0aa5afe99b7b5ef37a8061132f734bda6f72816abfbfd287e3574c353ed5fcd`; independent audit `.native-source/20261028/frontier-c/1200-1201-full-audit/report.md`, SHA-256 `8af6a917e521bd69f9e4e633bb9a01b5da824dac34677540876244f9b9b002f1`.

## Recheck findings

The prior version-boundary concern is closed by the accepted .0→.1 alias audit: it binds the full 4,786-file source inventories, rehashes all 1,019 movement-scope files, and reports the passenger-relevant entity/player/carrier files unchanged. The V1_20 change can therefore cover both 1.20 and 1.20.1 for this operation. The new `NativePlayerPassengerPlacement@V1_19_4` provider is the closest later provider for older selected profiles and returns empty; `V1_20` is selected only for the .0/.1 group and later profiles retain native behavior. This matches closest-later resolution.

The chest-boat correction supplies `0.15F` for chest boat and chest raft registry paths while regular boats receive `0.0F`. The apparent bamboo detection issue is not present: this adapter runs against current 26.2 entity registry IDs, where `bamboo_raft` and `bamboo_chest_raft` are distinct entity IDs. The 26.2 `EntityTypeIds.java` and `EntityTypes.java` hashes are `b8c7ec100ac4cfcb542cbb012045f2d2a3324d626da98fffc997ce3b06310d53` and `1b1b09812ed04d143f487d754aff5ed10ed696e8da04ce836ccbdbe0794d5a6c`; `BoatSplitFix.java` (SHA-256 `77a5c5e0323b5aba96e7ee0af3000ddd9bcf7c3bb9457261eab982ec09dc7083`) maps historical variant data to those IDs. `type.contains("bamboo")` therefore selects the old 0.25 offset for current bamboo raft types. The current IDs are included in the supported source-era type patterns, and the chest-raft path also receives the 0.15F side offset.

I rechecked the complete formulas and wrapper from the prior review. The adapter covers the old generic offsets and listed custom carrier writers, including Camel body-anchor animation, Chicken, horse rearing, Llama, Boat, Strider, and the remaining inherited offsets. The wrapper invokes the original carrier method once with the historical MoveFunction, retaining Boat/Chicken/Horse post-write effects; it restores the old horse body-yaw condition and reapplies Camel’s old clamp. Local client player/relation/side checks and native fallback remain intact. Merged main changes are semantically disjoint from the passenger files.

`git diff --check main...HEAD` passes and the worktree is clean. No build, tests, or runtime checks were performed. This review does not claim runtime parity.

