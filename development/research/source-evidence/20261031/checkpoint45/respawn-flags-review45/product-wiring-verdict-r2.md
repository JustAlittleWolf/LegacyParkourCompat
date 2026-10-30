# Product integration wiring review: respawn movement flags (revision 2)

**Verdict: ACCEPT, bounded to the accepted finding.**

- Reviewed product integration: `.native-integrations/respawn-flags-product45`, immutable commit `246fe89a7707c02ec2016af96c7e9a48ee8f8439`.
- This review supersedes the wiring correction request in `product-wiring-verdict.md`; the previously identified duplicate mixin registration is corrected.
- Independent candidate commit: `dd7f35b3479e5793492debfa52b224feb99b34cc`, accepted in `verdict.md`.
- Scope: product reconciliation, mixin registration, injection ordering/lifecycle, client thread, resolver boundary, discovery, and native fallback. No build or tests run.

## Re-review

Parsed `fabric.mod.json` and both referenced mixin JSON files. The fully-qualified names for `ClientPacketListenerRespawnMixin` and `EntitySharedFlagsAccessor` each appear exactly once across the registered configurations, both in `legacyparkourcompat.client.mixins.json`. The common configuration no longer repeats either name. This resolves the prior duplicate-application defect.

The corrected commit also retains the previously reviewed behavior: the old player's shared-flags byte is captured after `ensureRunningOnSameThread`, before vanilla swaps the player; handler tail reads the replacement byte and merges only bits 3, 4 and 7 for packets with entity-data keep bit 2 clear. The saved old-player state is consumed, and a failed expected player handoff throws. The raw accessor path does not call `setSprinting` or alter attributes. The hook has one `@MechanicType` registration and is automatically discovered; `V1_18_2` remains a lower native barrier, and deleting the redundant `V1_19_3` native class restores native fallback for that profile and later profiles because the `V1_19` implementation is inapplicable there.

Acceptance remains limited to the accepted finding's player movement flags and packet condition. The lower native barrier does not claim historical equality for older profiles. This review does not establish compilation or runtime parity.
