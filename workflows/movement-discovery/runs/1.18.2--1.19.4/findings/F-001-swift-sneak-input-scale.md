# F-001: Swift Sneak increases local sneaking input scale

- Older version A: Minecraft 1.18.2
- Newer version B: Minecraft 1.19.4
- Mechanic / coverage slice IDs: stage 1 local input scaling; stage 6 Swift Sneak/equipment
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic (Swift Sneak is absent from A's enchantment registry and appears in B's)
- First changed release: unknown within (1.18.2, 1.19.4]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `run.md` A / 1.18.2. A official client jar SHA-256 `1D09E3639644B6B2254499469D0765CC005A286D19F3FA595B0ED8FB07971EC7`; mappings SHA-256 `A2AA6EE1030BFEF79E9B2E08E79DE1637FDD7ECB5BF8891CF2E9A4B186042543`; Mojmap remapped jar SHA-256 `2D0C4B2EAC022E43DBE4706B7FE18C51547E4FBED86B675E92AB2040A9CF51D4`.
- B manifest artifact: `run.md` B / 1.19.4. Client SHA-256 `0E79CF7F07C107E9A1FE22ED703E472372B44149E64114944F0811ABBD25F3EC`; mappings SHA-256 `5EF270B938F89CFC77371E0DEEE9F9044C41D9A373FA11DCB1FD1923272C4606`; Mojmap remapped jar SHA-256 `3E40F8B67DC696F4E08F2F7AFD4D4C2E263B2A86221D13141953C7FDAA80375D`.
- A: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()`, lines 652–657; file SHA-256 `99C2D18BCD23243AFB8F95C5BAFB21FB0BE7EA04AACBB14FCF7BE7CED2C9C095`. It calls `input.tick(this.isMovingSlowly())`.
- A: `.../client/player/KeyboardInput.java`, `tick(boolean)`, lines 21–34; SHA-256 `281622F8481654035196A7BC1554D5251C1040518375E3AC6F6439E5EC894A75`. When the boolean is true it multiplies left and forward impulses by literal `0.3F`.
- A: `.../world/item/enchantment/Enchantments.java`, full registration class lines 1–end; SHA-256 `BB945530CB616FFE8C23156EC0BDD5819094C92D4FB808BD9254EDFDB7953BF2`. Its complete enchantment registration list has no Swift Sneak entry; the A tree has no `SwiftSneakEnchantment` implementation. This checked registration/class path establishes absence in this release, not just an unsuccessful member search.
- B: `decompiled_minecraft/1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep()`, lines 654–660; SHA-256 `8E7DA18F42D09FBB994F522C2B0E65FCB2BB83CABB21024360299D44D9674C58`. It computes `Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` before calling `input.tick(this.isMovingSlowly(), $$3)`.
- B: `.../client/player/KeyboardInput.java`, `tick(boolean, float)`, lines 21–34; SHA-256 `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`. It multiplies the two impulses by the passed float when the boolean is true.
- B: `.../world/item/enchantment/EnchantmentHelper.java`, `getEnchantmentLevel(Enchantment, LivingEntity)` lines 175–190 and `getSneakingSpeedBonus(LivingEntity)` lines 193–194; SHA-256 `C98B9E538AAE7FEA33A125560400F346745BAC993E859FFD3912DA58F3CC1128`. The helper scans the enchantment's eligible equipment slots and returns the maximum level found; the sneaking bonus is `level * 0.15F`.
- B: `.../world/item/enchantment/Enchantments.java`, registration line 31; SHA-256 `35FA4D1F9DA93971FA70BF6EC2F9218A8825FF343038B51143AE1404867F38E3`. Registers Swift Sneak as a legs-slot enchantment.
- B: `.../world/item/enchantment/SwiftSneakEnchantment.java`, constructor and `getMaxLevel()` lines 5–28; SHA-256 `6D68936DA23F57A2D9725155035B0A01D3B5017A899B5F9EC3C69F3E2256D64F`. Its equipment category is armor legs and maximum level is 3.

## Source-level difference

In A the local player applies a fixed `0.3F` crouching/slow-movement scale. In B the local player adds `0.15F` for each eligible Swift Sneak level, clamps the result to `[0.0F, 1.0F]`, and passes that scale to the input provider. With no Swift Sneak, B evaluates the same base scale. With level 1, 2, or 3 in an eligible leg slot and `isMovingSlowly()` true, the scale expression is respectively `0.45F`, `0.6F`, or `0.75F` (subject to the explicit clamp). Swift Sneak is a B-only mechanic in this endpoint pair, so this documents its modern movement effect and does not imply the older maps can contain it.

## Reachability and dependencies

Client keyboard state flows through `LocalPlayer.aiStep()` to `KeyboardInput.tick()`. `LocalPlayer.isMovingSlowly()` is true for crouching or visually crawling. The input-scale effect therefore changes locally simulated horizontal impulses in those states. The B helper reads equipment slots and the registered Swift Sneak level; actual equipped item/enchantment state may be supplied by the connected server. A client source finding does not determine how a server chooses or authorizes that state.

## Consequence and uncertainty

Source proves a larger input multiplier for crouch/visual-crawl movement when Swift Sneak is equipped in B. It predicts correspondingly larger movement input before later movement processing; no trajectory was simulated. Exact first introduction is unknown within `(1.18.2, 1.19.4]`. The class-level effect is bounded to the available release endpoints.

## Handoff

This is a B-only modern mechanic. Related stage 6 coverage must still compare other effects, enchantments, tags, equipment and attributes. No implementation or gameplay test is proposed here.
