# F-16: Wind Burst attacker impulse depends on sweep-target damage acceptance

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1209-1223.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1174-1184.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, doPostAttackEffects 190-221.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, doPostAttackEffects 190-221.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantment.java`, doPostAttack 260-280.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantment.java`, doPostAttack 260-280.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/damagesource/DamageSource.java`, getEntity 65-67.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/damagesource/DamageSource.java`, getEntity 65-67.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 1158-1193; bootstrap 227-243.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 1158-1193; bootstrap 227-243.

Difference: For a server-side player sweep attack where the target's `hurt`/`hurtServer` result is false, 1.21.4 still runs post-attack enchantment effects, while 1.21.5 skips them. If the attacking player holds a Wind Burst mace and satisfies the non-flying, fall-distance-at-least-1.5 condition, 1.21.4 therefore runs Wind Burst's explosion at the attacker and applies its direct player impulse; 1.21.5 does not. The source difference concerns the attack-effect dispatch gate. Damage calculation and outcomes are not emulated by this finding.

Movement meaning: The player must reach the sweep-attack loop, the target must reject the swept `hurt` attempt in 1.21.5, and the attacker must satisfy Wind Burst's direct-attacker predicate. The effect path then runs on the server through the attacker weapon's enchantment, `ExplodeEffect`, `ServerExplosion`, and `Entity.push`. The player impulse is suppressed when abilities-based flying is active and is scaled by explosion exposure and `EXPLOSION_KNOCKBACK_RESISTANCE`. This is separate from the primary-target hit path, whose dispatch site remains outside this bounded finding. In the stated branch, 1.21.4 can apply a Wind Burst impulse to the attacking player after a rejected sweep-target hit; 1.21.5 cannot.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "attack",
      "lines": "1209-1223",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "attack",
      "lines": "1174-1184",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java",
      "member": "doPostAttackEffects",
      "lines": "190-221",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5926f519897e629b369754a33fcd338140c57b930bb908bbd738ed430e38423c; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java",
      "member": "doPostAttackEffects",
      "lines": "190-221",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantment.java",
      "member": "doPostAttack",
      "lines": "260-280",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 67394bfb374d6f5bcd989dc46aa7353379a5bcfb767fcf5e2950c7ec485482e4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantment.java",
      "member": "doPostAttack",
      "lines": "260-280",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 67394bfb374d6f5bcd989dc46aa7353379a5bcfb767fcf5e2950c7ec485482e4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/damagesource/DamageSource.java",
      "member": "getEntity",
      "lines": "65-67",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b4f97ca0e3a7e9da20f3d277ffeada312b3027d321a0c976b3a0ce8239033b10; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/damagesource/DamageSource.java",
      "member": "getEntity",
      "lines": "65-67",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b4f97ca0e3a7e9da20f3d277ffeada312b3027d321a0c976b3a0ce8239033b10; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "1158-1193",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "1158-1193",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/enchantment/effects/ExplodeEffect.java",
      "member": "apply",
      "lines": "56-77",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c62612e19828b0684973b6ffb420cc891dd78526b5f92f0e9d1a71413930e367; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/enchantment/effects/ExplodeEffect.java",
      "member": "apply",
      "lines": "56-77",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c62612e19828b0684973b6ffb420cc891dd78526b5f92f0e9d1a71413930e367; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "227-243",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 01bdf94faa8089e50a282ec0b50c2b9956ddfb4260ae37dd9ec23e460e9d1bd1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "227-243",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/level/ServerExplosion.java",
      "member": "hurtEntities",
      "lines": "180-215",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/ServerExplosion.java",
      "member": "hurtEntities",
      "lines": "180-215",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d49f486abfa0d97a8da26f763c51c917c8b1f8064588962f21a3b113002c4f71; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
