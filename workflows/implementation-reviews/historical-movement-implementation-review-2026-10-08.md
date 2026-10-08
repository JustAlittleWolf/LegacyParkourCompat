# Historical movement implementation review — 2026-10-08

Scope: static review of four implementation patches against the accepted immutable findings and exact published Minecraft sources. No implementation files were changed; no build, tests, or runtime were run. The requested coordinator handoff `workflows/orchestration-2026-10-08/handoffs/01a1171c-9325-76e2-9670-fb4498142cdd.md` was absent from `feat/movement-completeness-final`. TICK-01 blind review was read from `feat/critical-1-8-source-review-2026-10-08`.

## TICK-01 sprint timeout — ACCEPT

Reviewed `3c531180287b3cecff49e557083db73352b258f7` against accepted finding snapshot `304b6a5` and blind review `workflows/coverage-review-2026-10-08/TICK-01.md`.

The patch moves the timer callback to `LocalPlayer.aiStep` HEAD. This puts the 600-tick expiration before local sprint eligibility and travel, matching 1.8.9 `LocalClientPlayerEntity.mobTick`, which decrements `sprintTimer` at the start and calls `setSprinting(false)` at zero. The change state uses 600 on sprint start and decrements once per callback; later profiles do not resolve the V1_8 hook. The exact source pair hashes match the finding: A `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`, B `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.

Open limits: the accepted first-change boundary is unknown within `(1.8.9, 1.9.4]`; this is source review only and does not validate trajectories or interactions with other sprint gates.

## Boat passenger yaw refresh — REQUEST CHANGES

Reviewed `8722bc476159b79f7e249f48b0e1076f209ced12` (handoff tip `48003fe`) against accepted finding snapshot `064fc24e5e3d8b4dab4f12c2000dcb13d37514ca` and review decision `4ff925e`.

The resolver direction is correct: `ChangeResolver` admits a change when `emulates >= selected` and picks the closest such change. The V1_17_1 callback’s exact-version guard therefore leaves V1_17 as a no-op; V1_18_2 does not resolve this change. This respects the accepted endpoint without asserting an unknown first changed release.

Two implementation mismatches remain. In 1.17.1, `LocalPlayer.startRiding(Entity, boolean)` updates `yRotO`, `yRot`, and `yHeadRot` only after a successful direct mount on a `Boat`; this patch writes only `yRot`. Also, the capture and tail checks use `Boat.hasIndirectPassenger(player)`. A player nested on another passenger is indirect, but rebuilding the Boat passenger list does not call that player’s `startRiding(Boat, ...)`; the patch would set their yaw to the Boat’s yaw when vanilla 1.17.1 would not. Exact A/B file hashes match the finding: LocalPlayer `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` / `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; packet listener `a59ba067bb0b9cdf026159a87534fd456ed766ec287cfd23e24356f84f078a5a` / `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b`.

Required follow-up: restore all three accepted yaw writes and constrain the emulation to the direct Boat passenger transition proven by A, or provide exact-source evidence that the nested case also writes those fields.

## 1.13 pose resize fit — REQUEST CHANGES

Reviewed `e6b61bd7f1334b7e4fa49a9bd0a5929e833338a8` in `build/codex-worktrees/movement-posefit` against frozen finding commit `448934e826fb41266dc79a313ef1187899d76382` and acceptance `10aa24e`.

The `Player.updatePlayerPose()V` HEAD hook is bound to the current shared player pose method. Registration at V1_13 and the V1_14 no-op profile boundary select the accepted 1.13.2 behavior for V1_13 while allowing native pose selection from V1_14 onward. The desired-pose precedence and candidate box anchor follow 1.13.2: fall-flying, sleeping, swimming/spin, sneaking, standing; candidate bounds start at the existing box minimum and use the requested width/height. The accepted source hashes are A `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` and B `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.

The fit-change gate is not tick-equivalent. 1.13.2 compares requested float dimensions to the entity’s `width` and `height` fields (`f != this.width || g != this.height`). The patch compares float dimensions to `AABB.getXsize()/getYsize()` double extents. Bounding-box construction can round those extents independently, changing whether the historical collision query and resize path runs. Compare against the current entity dimensions with the same float semantics. This finding’s fit result remains conditional on world collision providers; no tight-space outcome is claimed.

## Elytra jump start — REQUEST CHANGES

Reviewed `bedf96068bde90d295b6446aa4501b91ebf8f319` together with correction commit `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` on dedicated branch `fix/elytra-start-nan-gate`. The corrected `!(y < 0.0)` comparison exactly preserves the 1.14.4 strict `y < 0.0` gate, including NaN behavior; the earlier `y >= 0.0` form did not.

The rest of the hook still delegates to the 1.15.2 helper and remains nested under the 1.15.2 `LocalPlayer.aiStep` caller guard. That caller requires no flight-toggle that tick, not a passenger, and not on a ladder; the helper also rejects water. The 1.14.4 accepted gate has no such passenger/ladder/water exclusions and directly sends the request for an enabled Elytra after its descent/airborne/flying checks. Thus the patch suppresses ascending and NaN requests correctly but cannot restore the full accepted 1.14.4 eligibility in those states. The exact LocalPlayer hashes match the accepted finding: A `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`, B `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; the 1.15.2 Player helper hash is `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`.

Required follow-up: dispatch at a point that can reproduce the full accepted old gate and request behavior, or narrow the implementation contract/finding explicitly to the descending-state subset. The exact first changed release remains unknown within `(1.14.4, 1.15.2]`; server acceptance and glide trajectory are outside this review.

## Review limits

These decisions check source correspondence and patch structure only. They do not establish runtime parity, complete movement coverage for any version pair, or a first changed release inside an endpoint interval.
