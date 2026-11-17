# Independent audit: Powder Snow contact r2

## Verdict

**ACCEPT** for the bounded source comparison in report SHA-256 `4BF9123C7BA0C67710466245FA2D2CE4D32B0CAAE67963666C3C6F218BED0714`.

The r2 change addresses the sole REQUEST CHANGES item in the prior audit (`F1DC6CC12AC5C7DAD834B791BFDF286416E73B35ECD5B05C583FAE7C06462E78`): it adds a hash-bound client LocalPlayer tick-to-`LivingEntity.aiStep` path for the local-client reachability statement. The prior audit verified the unchanged Powder Snow callback, player flight gate, movement multiplier consumer, source identities, and five original source rows; those claims are reused here.

## Revision audit

The report hash matches the supplied r2 identity. A line comparison against r1 shows r2 adds only the “Local-player dispatch evidence” section and two Java source rows; the prior report content remains present unchanged.

The added call path is source-supported in both endpoints:

- `LocalPlayer.tick` calls `super.tick()` only under `connection.hasClientLoaded()` (`LocalPlayer.java:227–230`).
- `AbstractClientPlayer.tick` calls `super.tick()` (`AbstractClientPlayer.java:44–46`); `Player.tick` likewise calls `super.tick()` (`Player.java:231–259` in 26.1.2, `232–260` in 26.2). `Avatar` extends `LivingEntity` and contains no `tick` override.
- `LivingEntity.tick` invokes virtual `this.aiStep()` when not removed (26.1.2 `LivingEntity.java:2738–2740`; 26.2 `:2804–2806`), which dispatches for this instance to `LocalPlayer.aiStep`. `LocalPlayer.aiStep`, `AbstractClientPlayer.aiStep`, and `Player.aiStep` each reach their superclass implementation; `Avatar` contains no `aiStep` override, so the path reaches `LivingEntity.aiStep`.

The added `AbstractClientPlayer.java` SHA-256 is `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` at both endpoints. The added `Avatar.java` SHA-256 is `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` at both endpoints. Local files match their respective `unobfuscated.sources.sha256` rows. The report's existing endpoint ready/provenance/source/artifact identity values match the previously audited immutable r1 values, and no source identities changed.

## Scope limits

Acceptance remains limited to the report's stated source call path and Powder Snow movement-contact operation. This does not establish ordinary-world availability of the relevant in-block state, datapack or runtime registry behavior, trajectory impact, or pair-wide coverage/runtime parity.
