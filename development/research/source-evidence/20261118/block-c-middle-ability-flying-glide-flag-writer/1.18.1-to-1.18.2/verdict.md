# Independent audit — 1.18.1 → 1.18.2 ability-flight glide flag clear

## Verdict

**Narrow claim accepted; report wording needs one correction.** In the assigned ordinary local path, the ability-flight wrapper still clears shared flag 7 after its `super.travel(...)` call in both exact releases. In each release, `resetFallDistance()` is an earlier, separate write of `fallDistance = 0.0F`; the following `setSharedFlag(7, false)` clears the bit using the shared-flags byte mask. The clear operation, gate, ordering relative to movement, and separation from fall distance do not differ in this pair.

The source report’s final sentence, “The listed operation bodies and relevant gates match across the pair,” is too broad if it includes the cited `LivingEntity.travel` fall-flight branch. That reachable glide branch does contain a numeric movement difference: in 1.18.1, `float $$19 = Mth.cos($$15)` followed by a float cast/multiplication; in 1.18.2, `double $$19 = Math.cos($$15)` and double intermediates. This is upstream of the real `move(MoverType.SELF, ...)` call. It does not change the post-return order or the ability-flight wrapper’s flag clear, but it means the whole inherited glide method is not identical. Restrict the no-difference disposition to the ability-flight flag-clear/reset operation; record the adjacent glide arithmetic separately if the broader glide movement slice is in scope.

## Reproduced trace

For a local player who is already fall-flying, airborne, dry, not a passenger, has an enabled chest Elytra, and is lawfully toggled into `abilities.flying`:

1. `LocalPlayer.aiStep` permits `abilities.flying` to toggle only when `getAbilities().mayfly` is true; its Elytra-start input branch is gated by `!getAbilities().flying`. This trace starts with the glide flag already set, so no new start is required.
2. `LivingEntity.aiStep` calls `updateFallFlying()` immediately before `travel(...)`. The helper’s flag-7, airborne, nonpassenger, no-levitation gate and enabled-Elytra check preserve the local boolean as true; its final `setSharedFlag` write is guarded by `!level.isClientSide`, so this local path does not clear the bit there.
3. `Player.travel` takes `abilities.flying && !isPassenger()`, saves the incoming Y velocity and `flyingSpeed`, then calls `super.travel(...)`. The inherited fall-flight branch reaches `move(MoverType.SELF, ...)` before returning.
4. The player wrapper then restores horizontal velocity from the returned vector and Y to the saved value times `0.6`, restores `flyingSpeed`, calls `resetFallDistance()`, and only then calls `setSharedFlag(7, false)`. `Entity.resetFallDistance()` writes `0.0F`; `Entity.setSharedFlag` clears `1 << 7` in the shared-flags byte.

Thus the checked local end-of-travel state has fall distance reset separately and flag 7 clear. This source trace does not claim a resulting trajectory or runtime parity.

## Exact claim and evidence identity

Audited input report: `c-mid-1181-1182-ability-flight-glide-clear-source/report.md`, SHA-256 `8a960b012e22954b9812ba133c08ba59f66df39c4c5ac744bd72a6ffa51ef01e` (verified before reading).

Read-only decompiled roots: `decompiled_minecraft/1.18.1/mojmap/` and `decompiled_minecraft/1.18.2/mojmap/`. Both ready markers identify their exact release, Mojmap, Vineflower, Java 25, and the matching source-manifest digest. Local provenance/ready/source-manifest/artifact-manifest/diagnostics hashes were checked against the input report:

| Identity | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `mojmap.provenance.json` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| `mojmap.ready.json` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` |
| `mojmap.sources.sha256` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| `artifacts.sha256` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |
| `movement-diagnostics.txt` | `0e3e7d1310deeb834a8c1598ad63f24fcff01fcb26efce326945fcc42ecc3ecd` | `0bc8857ee048b0ede8e696f3b7b050006618b278936cf182b3464c6c99483ac8` |

The four cited Java source files’ exact SHA-256 bytes were recomputed and each matched its row in that release’s source manifest:

| Source | 1.18.1 | 1.18.2 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

## Source locations

- `LocalPlayer.aiStep`: 1.18.1 lines 725–750; 1.18.2 lines 720–745.
- `LivingEntity.aiStep` calls `updateFallFlying()` then `travel(...)`: 1.18.1 lines 2542–2549; 1.18.2 lines 2546–2553.
- `LivingEntity.updateFallFlying`: 1.18.1 lines 2586–2611; 1.18.2 lines 2590–2615. Local flag write remains server-guarded in both.
- `Player.travel` ability-flight wrapper: 1.18.1 lines 1409–1438; 1.18.2 lines 1422–1451. The save/super-call/restore/reset/clear operation order matches.
- Fall-flight movement and actual `move` call: 1.18.1 `LivingEntity.travel` lines 2070–2112; 1.18.2 lines 2074–2116. The glide math noted above differs; both call `move` before the same server-only grounded clear.
- `Entity.setSharedFlag`: 1.18.1 lines 2022–2029; 1.18.2 lines 2035–2042. `resetFallDistance`: 1.18.1 lines 2101–2103; 1.18.2 lines 2114–2116.

## Evidence limits

The manifests and source bytes are local and pinned; no decompilation, generation, tests, runtime, product/history, wiki or network activity was used. The referenced cached client/mapping artifacts are not retained at the declared campaign artifact paths, so their original raw bytes could not be rehashed in this audit. The 1.18.1 provenance embeds its original client and official mapping hashes; the 1.18.2 ready marker embeds its client hash while its short provenance summary does not embed full raw-input records. This does not affect the recomputed source-byte and source-manifest checks above, but leaves raw-client-byte revalidation open.

Audit identity: this file’s SHA-256 is supplied to the assigning coordinator after write. This digest pins the exact verdict bytes; it is not a public-key digital signature.
