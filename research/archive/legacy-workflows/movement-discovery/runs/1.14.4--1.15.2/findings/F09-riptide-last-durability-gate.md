# F09: 1.15.2 blocks Riptide use at one remaining durability

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 4 and 6; player item-use impulse
- Classification: changed activation gate
- Confidence: source-confirmed
- Applicability: player tries to activate Riptide while the Trident has exactly one durability point remaining
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `TridentItem.use` rejects the item only when `damageValue >= maxDamage`; at `maxDamage - 1`, a Riptide Trident can start use if the player is in water or rain. A `TridentItem.java` SHA-256: `C1D13346913255983356BF64A8B63502EA736D37F04055DA18D266B1BA024476` (lines 117–128).
- B `TridentItem.use` rejects at `damageValue >= maxDamage - 1`, so that same one-durability state cannot start use. The water/rain gate remains. B `TridentItem.java` SHA-256: `9DFE4EC0E5159359285F1B7C50379795FAC78F0A404ECED1B1ED561B321531FB` (lines 111–122).
- Both `TridentItem.releaseUsing` methods accept a charged Riptide level and compute the same look-direction impulse using `3.0F * ((1.0F + level) / 4.0F)`, normalize it, call `player.push`, start auto-spin attack, and move a grounded player upward by `1.1999999F`. A lines 57–112; B lines 51–106. The difference is the B use gate before this shared release path.
- `EnchantmentHelper.getRiptide` delegates to the same Riptide enchantment lookup (A hash `EDD49C2FBC3B557441FAE63D5FBCAAE0374DE85562376EC7859AA877AA83A5F4`; B hash `AC48EA19FD7B49226D4887086B1C907297E79454243C37559AE49DCAA504FCDD`). `Enchantments.RIPTIDE` retains the same registration (`Enchantments.java` SHA-256 `8051D941C7A008D7801CAB4F45DACC8E7EC13B45F7880AC664ED982185879490`); `TridentRiptideEnchantment.java` is byte-identical (SHA-256 `F8CEBAC038B65AD4DF4C2C11AF400B343FA0A6780BF7CDDCDC5C672E90B66AE6`).

## Source-level difference

With exactly one durability remaining, A permits starting a charged Riptide use; releasing it follows the shared impulse path, even though the server-side durability cost can break the Trident. B rejects starting use at that durability value, so the player cannot receive this impulse through that use. This is a player movement difference caused by item-use availability.

## Reachability and boundary

Player item use -> `TridentItem.use` durability and environment checks -> charged release -> `player.push` and grounded vertical move. The source confirms the activation boundary and downstream impulse. It does not simulate damage or durability behavior beyond the source-level gate.

## Consequence and uncertainty

This condition is narrow to a nearly broken Trident with Riptide. No gameplay test was performed; the first release with the changed threshold is unknown within the endpoint interval.
