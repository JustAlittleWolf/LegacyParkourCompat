# F-17: Sweep-target knockback now requires damage acceptance

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S7-PUSH
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player sweep attacks against a target Player whose damage is rejected
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `Player#attack` sweep loop lines 1209-1223 unconditionally calls `LivingEntity#knockback(0.4F, sin(yRot), -cos(yRot))` at lines 1216-1218 before calling `LivingEntity#hurt` at line 1219; server-side post-attack effects follow at 1220-1222. `Player.java` SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `Player#attack` sweep loop lines 1174-1184 calls `LivingEntity#knockback(0.4F, sin(yRot), -cos(yRot))` only inside the server-side `hurtServer(...) == true` branch at lines 1177-1182. `Player.java` SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`.
- Shared direct movement consumer: A `LivingEntity#knockback` lines 2101-2104 / B lines 2125-2128 calculates and writes target delta movement; paired `LivingEntity.java` hashes are `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30` / `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`. The bounded knockback formulas match; only the Player sweep caller gate differs.

## Source-level difference

For a swept target Player whose `hurt`/`hurtServer` attempt returns false, A still applies the sweep knockback impulse, while B skips it together with the post-attack effects. The direct target velocity writer is the movement behavior in scope here; attack and damage outcomes are not emulated by this finding.

## Reachability and dependencies

The attacking player must enter the sweep-attack loop and the swept target must be a Player. When that target rejects damage, A still calls its knockback method; B does not. The target's `LivingEntity#knockback` then writes its velocity in A, after which the target's regular player movement path consumes that state. This finding is distinct from F-16, which covers the Wind Burst impulse applied to the attacking player through the same changed damage-acceptance gate.

## Consequence and uncertainty

In the stated branch, A applies a directional sweep impulse to the target Player and B leaves the target's velocity unchanged by that sweep call. Exact trajectory and downstream persistence are not established here. Runtime validation was not performed.

## Snapshot source/artifact identity

- A source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; client jar SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`.
- B source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; client jar SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.

## Handoff

Independent delta: the 1.21.5 sweep-target damage-acceptance gate suppresses the direct sweep knockback impulse after a rejected hit. Related slice: S7-PUSH. Pending independent source review; no implementation handoff.
