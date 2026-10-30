# Correction review: Minecraft 1.8.1 profile speed provider

Reviewed product commit: `405b19c3bf1bfdc135b083a419cae7d1e37e1d10`, on top of `2b485d8631cf57389b46763774a574781fafe1fb`.

## Decision

**ACCEPT.** The correction closes the only issue identified in the prior integration review.

## Review evidence

The commit is a 95%-similarity rename from `change/v1_8/UncappedPlayerMovementSpeed.java` to `change/v1_8_1/UncappedPlayerMovementSpeed.java`. Its only source changes are the package declaration, `@MovementChange(emulates = ParkourVersion.V1_8_1)`, and the applicability wording in the class comment. The method body and calculation gates are unchanged.

The enum still orders the groups chronologically: `V1_8` (`1.8`), `V1_8_1` (`1.8.1`–`1.8.4`), `V1_8_5` (`1.8.5`–`1.8.8`), then `V1_8_9` (`1.8.9`). Existing `id()`/`patches()`-based parsing and selection therefore map the canonical `1.8.1` ID and every patch in that group to `V1_8_1`; the group label is `1.8.1-1.8.4`. Config and handshake use those generic IDs and remain consistent.

`ChangeResolver` chooses the minimum registered `emulates` ordinal greater than or equal to the selected profile. Thus the moved `V1_8_1` provider is selected for profile `V1_8` as its closest later applicable implementation, preserving the old uncapped calculation for exact 1.8 (whose source was checked as uncapped). It is also selected for profiles `V1_8_1` through `V1_8_4`. For `V1_8_5` and later, it is inapplicable and the existing later/native capped behavior remains available. `MovementChangeDiscovery` scans version subpackages matching `v<digits>[_<digits>...]`, so the new package remains discoverable without a catalog or loop change.

The source boundary remains the previously accepted one: exact 1.8/1.8.1 source declares the movement-speed maximum as `Double.MAX_VALUE`; the accepted 1.8.2–1.8.4 source closure establishes the same through 1.8.4; exact 1.8.5 declares `1024.0`. No calculation logic changed, so the repair restores the missing profiles while preserving that boundary.

## Limits

Static review only. No build, test, game launch, decompilation, or runtime parity claim.
