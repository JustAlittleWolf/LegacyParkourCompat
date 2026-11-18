# Independent audit verdict: 1.15.1 → 1.15.2 ability flight / Elytra clear

**Verdict: ACCEPTED for the assigned bounded claim.** The source report is accurate for a logical-client local player in dry air, airborne, not riding, with active `abilities.flying`, flag 7 already set and a fly-enabled chest Elytra. It correctly distinguishes the new ladder gate on *starting* Elytra flight from the already-active ability-flight path. This accepts only the stated bounded operation; it does not freeze the release pair.

## Independent trace

Both versions call `updateFallFlying()` immediately before virtual `travel(...)` in `LivingEntity.aiStep`. The method preserves an existing flag-7 state on the client when airborne, not a passenger, and wearing an enabled Elytra; it only writes the derived flag on the logical server. The cited `ElytraItem.isFlyEnabled` body and file bytes match.

For the assigned ability toggle, `Player.travel` enters `abilities.flying && !isPassenger()`, records Y velocity and flying speed, then calls `super.travel`. In the dry-air Elytra branch, `LivingEntity.travel` computes glide velocity and calls `move(MoverType.SELF, ...)`; `Entity.move` updates the bounding box and position. After that call returns, `Player.travel` restores X/Z from the travel result, resets Y to the saved velocity times `0.6`, restores flying speed, writes `fallDistance = 0.0F`, then clears flag 7 using `setSharedFlag(7, false)`. `setSharedFlag` clears the selected bit with `b & ~(1 << i)`. These operations and ordering are the same in both releases. The separate server-only grounded clear in `LivingEntity.travel` is not the clear in the assigned airborne client path.

The only nearby player-movement difference in these slices is `LocalPlayer.aiStep`: 1.15.2 adds `!this.onLadder()` to the jump-to-start condition. The attempt already requires `!this.abilities.flying`; it therefore cannot gate the active ability-flight case under review.

## Bound evidence

Canonical roots are `decompiled_minecraft/<version>/mojmap`. Source manifests and ready markers identify the same published sources as the source report; I recomputed the listed hashes for every relied-on source file and confirmed their corresponding manifest entries. Entity.java is byte-identical. File SHA-256 values:

| Source | 1.15.1 | 1.15.2 |
|---|---|---|
| `LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | same |
| `ElytraItem.java` | `701e25f13e9ba8db74aca774f404a5281b2a58f4f7dc72e3aeabac76bbf28b43` | same |


| Version | Ready marker SHA-256 | Source manifest SHA-256 | Provenance file SHA-256 | Client SHA-256 |
|---|---|---|---|---|
| 1.15.1 Mojmap | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |
| 1.15.2 Mojmap | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

The 1.15.1 provenance is run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`; 1.15.2 is the Mojmap batch `1.15.2,1.16.5,1.17.1,1.18.2`. The 1.15.2 provenance file does not publish raw input hashes and is not hash-pinned by its ready marker; the ready marker does pin the source-manifest digest and client SHA. Acceptance is bound to those ready/source identities and the actual source-file bytes above, not to an independently reconstructed raw 1.15.2 client/mapping pipeline.

## Accepted report identity

`c-mid-1151-1152-ability-flight-glide-clear-source/report.md` SHA-256: `57211f6c2e35d7aa9539ea663c6c4833f8490c61ee7d633e6deeeef684e5d085`.

Signed: `/root/c_mid_1151_1152_ability_flight_glide_clear_audit`, independent exact-source reviewer. Scope remains the assigned Elytra ability-flight/flag-clear path and adjacent start gate only.


