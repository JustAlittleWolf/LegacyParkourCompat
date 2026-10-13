# Independent review: swimming entry gate and Big Dripleaf

## Verdict

**ACCEPT** the swimming-entry implementation at code commit `5c419e8d950cf8e62174a4562cc352a1d952747f` (branch `fix/mcpk-1-17-movement-reconcile`, supplied final main-merged tip `7376c93a5dbdc3313482cd9f04c5742650756b54`). No static correctness issue was found in the requested scope.

**ACCEPT — no code** for the Big Dripleaf reconciliation at `d3733a4872cb8735efa90b2b2a83553dcf2022d3`. Native block collision and tilt lifecycle already cover the available versions; adding an emulation would be incorrect.

This review branch was based on the implementation commit and then merged current `main` at `b099aa02d82e57c1672f68375edcbcacdf8e3973` in merge commit `161a02a7af1a84809be1405093d01ae6a02c3e68`. The intervening main changes add fence end-portal-frame work and update campaign documentation; none touches the swimming hook, resolver, or its registration, and the merge had no conflict.

## Swimming implementation

The reviewed source change introduces a dedicated `player.swimming.update` mechanic. `SwimmingUpdate` is registered at `V1_16_2`, preserves the already-swimming branch (`sprinting && in water && !passenger`) and entry branch (`sprinting && underwater && !passenger`), and omits the later block-position water condition. For selected profiles before `V1_13`, it returns false before either branch.

The hook is in common `EntityMixin` at `Entity.updateSwimming()V` HEAD. It only applies to `Player`; when a behavior resolves it writes the swimming flag and cancels the remainder of that method. Non-player entities fall through to vanilla. `Player.updateSwimming()` retains its flying check before delegating to `Entity.updateSwimming()`, so flying players still have swimming cleared before the injection can run. The common mixin is loaded on both sides; the behavior has no client-only or server-only branch.

Resolver applicability is correct for the requested boundaries. `ChangeResolver` returns no changes for `CURRENT` and otherwise selects changes whose emulated version is at least the selected version. Thus the `V1_16_2` behavior applies to selections through the `V1_16_2` group (which includes 1.16.5), while `V1_17`, `V1_17_1`, later profiles, and `CURRENT` retain native behavior. The `V1_12` swimming-state hook is a separate `player.swim` key and still returns false; the new early return also prevents the update hook from starting swimming for selections older than `V1_13`. Existing `V1_13` swimming-pitch correction uses a separate hook and is unaffected.

Exact-source comparison found the same continuation and entry branch structure in 1.13.2, 1.14.4, 1.15.2, 1.16.1, 1.16.2, and 1.16.5. In the older 1.13.2 source, the underwater accessor is named `isSubmergedInWater`; later checked sources use `isUnderWater`. The 1.17.1 and 26.2 endpoints retain the continuation branch and add `level.getFluidState(blockPosition).is(FluidTags.WATER)` only to the entry branch. The hook therefore does not apply the post-1.16.5 entry gate to 1.13–1.16 selections.

The 1.16.5, 1.17.1, and 26.2 `baseTick()` paths update fluid state and eye-fluid state before `updateSwimming()`. In 1.16.5 and current 26.2, `isUnderWater()` reads the eye-water flag and `isInWater()`. The hook consumes these already-maintained vanilla state inputs; it does not duplicate their producers. The 1.13.2 source likewise updates its submerged-water flag before `updateSwimming()`.

## Big Dripleaf no-code disposition

The 1.16.5 `Blocks.java` has no Big Dripleaf registration. The block exists in 1.17.1 and 26.2. At both endpoints, its leaf collision shape is unchanged by state: `NONE`/`UNSTABLE` occupy Y 11/16–15/16, `PARTIAL` occupies Y 11/16–13/16, and `FULL` is empty. The separate outline shape includes the stem and is not the collision provider. Both sources schedule `UNSTABLE` and `PARTIAL` transitions after 10 ticks and `FULL` reset after 100 ticks; entity contact drives tilt on the server. This is native block state, collision, callback, and scheduled-tick behavior. No historical registration or lifecycle emulation should be added to pre-1.17 profiles.

## Evidence identity and limits

- Swimming finding snapshot: `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md`, SHA-256 `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32`; independent clean review `6e045f3d50f9248ab329c5bd698caa935e8a9be3` accepted it.
- Swimming source endpoints: 1.16.5 Mojmap `Entity.java` SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`, `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; 1.17.1 Mojmap `Entity.java` `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`, `Player.java` `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`; current 26.2 unobfuscated `Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`, `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- Intermediate `Entity.java` source identities checked for the older branch: 1.13.2 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; 1.14.4 `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`; 1.15.2 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; 1.16.1 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; 1.16.2 and 1.16.5 share `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- Big Dripleaf snapshot: `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot-r2.md`, SHA-256 `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee`; independent clean review `6e045f3d50f9248ab329c5bd698caa935e8a9be3` accepted it. Exact checked source hashes: 1.16.5 `Blocks.java` `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`; 1.17.1 `Blocks.java` `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`, `BigDripleafBlock.java` `1e1315b0a53eb85a3365335131e7819c1b667a55e14343d839c5211c671fd59f`; 26.2 `Blocks.java` `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`, `BigDripleafBlock.java` `6442924185357e52fb41d110596766cc98140058e5484af7c1ea04610bed816d`.
- Both preserved MCPK page fetches returned HTTP 403. Findings are supported by the exact vanilla source snapshots and accepted clean reviews, not by fresh confirmation of live page wording. The endpoint comparison does not establish the first changed 1.17 patch or close the wider source pair.
- This is a static review only. No build, tests, client/server, or runtime movement validation was run.
