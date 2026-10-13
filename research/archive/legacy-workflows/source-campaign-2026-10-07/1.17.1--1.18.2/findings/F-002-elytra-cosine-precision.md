# F-002: Fall-flying coefficient changes from float-table to double cosine

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-ELYTRA
- Classification: changed player movement behavior
- Confidence: source-confirmed for the guarded travel coefficient; downstream velocity difference inferred from the changed arithmetic
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Exact source and artifact identity

A: version metadata 1.17.1, Mojmap; source manifest SHA-256 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b; 41-entry artifact manifest SHA-256 e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa; mapped jar SHA-256 2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c.

B: version metadata 1.18.2, Mojmap; source manifest SHA-256 aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a; 41-entry artifact manifest SHA-256 a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036; mapped jar SHA-256 60a2017dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba.

## Paired movement source evidence

A LivingEntity#travel lines 2074-2090, SHA-256 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f, computes the pitch cosine with `Mth.cos` into a float before using it in the fall-flying lift/pull equations.

B LivingEntity#travel lines 2080-2099, SHA-256 db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782, computes `Math.cos` and retains the double result in those equations. The branch is guarded by `isFallFlying()` in both versions.

## Reachability and activation guards

LocalPlayer#aiStep A lines 747-751 / B lines 740-744 reads jump input and, when the player is airborne, not already fall-flying, not ability-flying, not a passenger and not on a climbable, checks the chest-slot item and `ElytraItem.isFlyEnabled`. On a valid Elytra it calls `tryToStartFallFlying()` and sends the same START_FALL_FLYING command. LocalPlayer.java hashes are A c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812 / B 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.

Player#tryToStartFallFlying A line 1542 / B line 1555 sets the shared fall-flying flag only when the player is not on ground, not already fall-flying, not in water, and has no Levitation effect; it also requires a fly-enabled chest Elytra. `ElytraItem.isFlyEnabled` A/B line 21 requires damage value < max damage - 1 (class hashes A 6d8923e81f97dc290d7e80cc76647df2c4f1960460eb4a6c8a89ea91a4f0f6c0 / B f6859e9d245a2b34c4e2c24af5bf6a2bb956f05ba8eaf3dbef74ba543ea0993c). The server command handler repeats `tryToStartFallFlying` and stops the flag if the request is not eligible. The paired client and server activation paths therefore reach the guarded travel branch with an intact chest Elytra and the same entry requirements.

A reachable setup is a player with a fly-enabled Elytra in the chest slot, falling outside water and without Levitation, who presses jump while not passenger, ability-flying or on a climbable. The identical guards activate fall-flying before subsequent travel ticks.

## Source-level consequence and uncertainty

Once the guarded branch is active, the same lift/pull formula receives a float-table cosine in A and a double cosine in B. This proves a historical arithmetic change in player travel. A particular resulting velocity or trajectory delta is inferred and was not numerically simulated; modifier data and unrelated movement branches remain outside this bounded finding.

## Dependency closure and release boundary

The fall-flying travel branch, local input trigger, chest-slot/durability eligibility, Player state guard and server request guard are checked on both versions. D-ELYTRA-ENTRY is resolved for this mechanic; attributes, effects and enchantments remain open in the wider modifier inventory. The first changed release is not established; the proven boundary is 1.17.1 versus 1.18.2 only.

## Handoff

Snapshot eligible for blind source review. Pair discovery remains partial; no pair freeze or runtime parity claim.
