# 1.14.4 → 1.15: creative-flight toggle during live Elytra glide

## Verdict

**No source difference found for the assigned constrained tick.** For an ordinary dry, airborne, nonpassenger local player with a default chest Elytra, an already-live fall-glide flag (shared flag 7), and creative flight legally toggled on this tick, both releases run the same move sequence: `Player.travel` selects its creative-flight branch, calls `LivingEntity.travel` while flag 7 is still set (so the live glide equations perform this tick’s move), then restores vertical velocity to the pre-travel value multiplied by `0.6`, restores `flyingSpeed`, and clears shared flag 7. The local post-`super.aiStep()` auto-disable is unreachable on this airborne tick. Thus the glide is cleared after the glide move in both versions.

## Reachability and scheduler

`LocalPlayer.aiStep` in each release can toggle `abilities.flying` when `abilities.mayfly` is true, the jump input has just risen (`!bl`), `input.jumping` is true, and the input is not auto-jump-generated. When `jumpTriggerTime` is zero it arms the timer to 7; on the subsequent qualifying press it toggles flight, calls `onUpdateAbilities`, and resets the timer to zero. Those toggle gates and timer writes are the same for this case. `super.aiStep()` follows the input handling, so the newly true `abilities.flying` is visible to the movement dispatch; the ordinary airborne state avoids LocalPlayer’s later on-ground flight disable.

The 1.15 local-player code adds a per-tick `bl8` latch and changes the fresh-glide request to call `tryToStartFallFlying()`, with an explicit passenger check. Those edits do not change the assigned live-glide case: after flight toggles on, `!abilities.flying` already fails in 1.14’s inline request guard; in 1.15 the latch also suppresses the request, while `tryToStartFallFlying()` would reject an already-live glide. Neither side sends a new glide-start command for the stated tick. The changed fresh-glide conditions (including downward velocity / water checks) are outside this constrained live-glide witness.

## Move and post-move clear

- 1.14.4 `Player.travel(Vec3)`, lines 1380–1408: the `abilities.flying && !isPassenger()` branch saves old vertical velocity and flying speed; calls `super.travel`; applies old-Y × `0.6`; restores flying speed; then writes `fallDistance = 0.0F` and `setSharedFlag(7, false)`; movement statistics follow.
- 1.15 `Player.travel(Vec3)`, lines 1456–1486: same gates and operation order, with coordinate accessors replacing direct coordinate fields in the initial/delta calculations.
- 1.14.4 `LivingEntity.travel(Vec3)`, lines 1782–1835, and 1.15 lines 1831–1884: the live `isFallFlying()` movement equations, arithmetic order, drag factors, `move`, and grounded/server flag-clear gate match. Under the assigned client airborne witness, the grounded/server clear is false; `Player.travel` performs the reachable flag-7 clear after the movement call.
- The adjacent `fallDistance = 0.0F` in the creative-flight branch is a separate writer and is not combined with this flag-7 operation verdict.

## Exact source identity

Canonical read-only source trees: `decompiled_minecraft/1.14.4/mojmap/` and `decompiled_minecraft/1.15/mojmap/`. Both ready markers report `status: ready`, exact matching version and `mapping: mojmap`; the selected source manifests’ SHA-256 values equal their ready-marker `sourceManifestSha256` fields. The 1.14.4 directory also contains other mapping outputs; only its complete manifest-backed Mojmap tree was used. The 1.14.4 Yarn staging directory was not used.

| Release | Ready marker SHA-256 | Provenance manifest SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.14.4 Mojmap | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.15 Mojmap | `c6b66b63bafaf2096ad760611f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `de4a538ebbaaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |

Original relied file SHA-256 values (each independently matched its source-manifest entry):

| Relative source file | 1.14.4 | 1.15 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
