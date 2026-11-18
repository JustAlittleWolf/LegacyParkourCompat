# Independent source audit: 1.11.2 → 1.12.0 pitch path

## Verdict

The ordinary local pitch-turn setter has **no source difference** between 1.11.2 and the 1.12.0 release (the exact Minecraft version ID and source root are `1.12`). Do not report a pitch-setter change for this pair.

A separate, movement-relevant dependent change exists at the first look-vector read in the dry Elytra branch: the vector's horizontal heading changes from `headYaw` to `yaw` when those fields differ. The glide branch's explicit current-pitch math is unchanged. Keep the look-vector change separate from the unchanged pitch setter.

## Direct local turn: no difference

`GameRenderer` calls `minecraft.player.updateLocalPlayerCamera(h, i * j)` in both smooth and unsmoothed camera branches (1.11.2 `net/minecraft/client/render/GameRenderer.java:855-866`; 1.12 `.../GameRenderer.java:856-867`). The method is the same in both `net/minecraft/entity/Entity.java` versions: save current pitch in `f`; compute `(float)(this.pitch - pitch * 0.15)`; clamp current pitch to `[-90.0F, 90.0F]`; then update `lastPitch` as `lastPitch + (pitch - f)` (1.11.2 lines 304-311; 1.12 lines 307-314). `MathHelper.clamp(float, float, float)` has the same `< min` / `> max` comparisons and return behavior at 1.11.2 lines 80-85 and 1.12 lines 80-85.

Finite witness, using the method's finite pitch-delta argument: current pitch `89.0F`, previous pitch `80.0F`, argument `-20.0F`. The casted current result is `92.0F`, clamped current pitch is `90.0F`, and previous pitch becomes `80 + (90 - 89) = 81.0F`. Both releases produce the same values and operation order. This is a concrete clamp witness, not a release delta.

## First downstream pitch use: dry Elytra

In both releases `LivingEntity.moveRelative` gates movement to locally controlled or logical-side-updated entities and then excludes water/lava (unless player flight is active) before the `isFallFlying()` branch (1.11.2 lines 1388-1397; 1.12 lines 1425-1435). In that branch, `this.getLookVector()` is read first, followed by explicit current-pitch conversion `float i = this.pitch * (float)(Math.PI / 180.0)` (1.11.2 lines 1392-1403; 1.12 lines 1429-1440). The Elytra-specific equations following those lines are text-identical across the pair: the look vector feeds horizontal normalization/alignment, while `i` feeds cosine lift and the negative-pitch dive term. The route reads current pitch; `lastPitch` is not consumed there.

The dependent getter resolution changed:

- **1.11.2:** `LivingEntity.getLookVector()` returns `getRotationVector(1.0F)` (`LivingEntity.java:1915-18`). Its override handles `1.0F` by calling `getRotationVector(this.pitch, this.headYaw)` (`LivingEntity.java:1921-24`). The base `Entity.getLookVector()` itself returns `null` (`Entity.java:1664-66`).
- **1.12.0 / version ID `1.12`:** the `LivingEntity.getLookVector()` override is absent. The inherited `Entity.getLookVector()` directly calls `getRotationVector(this.pitch, this.yaw)` (`Entity.java:1690-92`), bypassing the still-present `LivingEntity.getRotationVector(float)` override, which would use `headYaw` at `tickDelta == 1.0F` (`LivingEntity.java:1960-63`). The shared trigonometric helper is unchanged (1.11.2 `Entity.java:1216-22`; 1.12 `Entity.java:1239-44`).

Thus the pitch component remains current `pitch`, and the explicit Elytra pitch equations do not change; the look vector's yaw input changes from `headYaw` to `yaw`. This can rotate the glide's horizontal steering when the two fields differ. The local camera method changes `yaw` but does not write `headYaw`; the living-entity constructor initially copies yaw into headYaw (1.11.2 `LivingEntity.java:168-72`; 1.12 `LivingEntity.java:171-75`). For a finite witness, start with yaw/headYaw `0F`, then pass camera yaw delta `10F` and pitch delta `0F`: yaw becomes `1.5F`, headYaw remains `0F` at method return, so the old and new look-vector headings differ on a subsequent dry Elytra read. This witness isolates the dependent yaw-source change; it does not imply a pitch-setter delta.

A distinct adjacent math change appears only in the non-Elytra fallback branch of the same `moveRelative` method: its `updateVelocity` call grows from `(sideways, forwards, scale)` (1.11.2 line 1456) to `(forwards, sideways, upwards, scale)` (1.12 line 1493). It is not part of the pitch/look-vector verdict above and should be tracked as a separate operation if included in broader pair coverage.

## Evidence identity and byte checks

- Older source root: `decompiled_minecraft/1.11.2/ornithe-feather`; provenance says exact `versionId=1.11.2`, namespace `ornithe-feather`, Feather Gen2 `1.11.2+build.2`; the ready marker pins the client SHA-256 and the SHA-256 of `ornithe-feather.sources.sha256` is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. The cited files' actual SHA-256 values match entries in that source manifest.
- Newer exact release: `1.12.0` is represented by Mojang version ID `1.12` and source root `decompiled_minecraft/1.12/ornithe-feather`; pinned inputs name `/1.12.json`, Feather metadata `/feather/1.12`, Feather Gen2 `1.12+build.2`, and client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`.
- The hidden previous tree `decompiled_minecraft/1.12/.ornithe-feather.previous-799a08bb-c1f0-44b2-8d8f-6b2334a44a8c` and version-root tree have byte-identical `.decompile-provenance.json` (SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`) and byte-identical cited source files. In both trees, actual source hashes equal the provenance entries.
- Cited source file SHA-256s, in order `Entity.java`, `LivingEntity.java`, `GameRenderer.java`, `PlayerEntity.java`, `MathHelper.java`:
  - 1.11.2: `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`; `d8f54f58020e52b117c74cf248a1bac4fc541c4244340f6a0b43f5dbce245db5`; `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`; `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b`.
  - 1.12.0: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`; `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063`; `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80`.

This is an endpoint comparison only; it does not establish the exact intermediate patch that introduced the look-vector change.
## Phase-two report comparison

The authorized source report was byte-verified at SHA-256 `bc4b336327900a5da73303f1f94a6a94f322bc323347d0327ccf918b9e6fc1e6`. Its core verdict matches this frozen audit: the direct pitch setter is unchanged, and the separate downstream look-vector operand changes from `headYaw` to `yaw`; the Elytra branch's explicit pitch math is unchanged. Its finite witness (`pitch=89F`, `lastPitch=88F`, input `-100F` → unclamped 104F, clamped 90F, previous 89F) and this audit's witness (`89F`, `80F`, input `-20F` → clamped 90F, previous 81F) are both finite examples of the same unchanged sequencing.

Source readiness is qualified. The 1.12 version-root and hidden previous tree have the same provenance bytes and cited-source bytes, and the cited version-root source hashes match its provenance map. However, 1.12 has no `ornithe-feather.ready.json` and no separate `ornithe-feather.sources.sha256`; therefore this is provenance-backed exact-source evidence, not a campaign-ready marker. The older 1.11.2 root does have a verified ready marker and source manifest. The report correctly does not claim live-client frequency for the `yaw != headYaw` precondition. My finite yaw example establishes the camera method's immediate field result only, not runtime timing or parity.

The non-Elytra `updateVelocity` call difference noted in the frozen prefix is an adjacent, separate operation outside this pitch-slice report; it is not part of the accepted look-vector conclusion.