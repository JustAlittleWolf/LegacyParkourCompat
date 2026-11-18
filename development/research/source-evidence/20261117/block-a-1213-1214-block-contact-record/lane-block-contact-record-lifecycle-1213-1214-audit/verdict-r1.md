# Additive independent verdict — corrected r1 lifecycle claim

## Result

**ACCEPT** the exact corrected r1 lifecycle claim below. This rebinds the prior bounded lifecycle acceptance to the corrected claim revision. The separately accepted block-cell traversal claim is unchanged; this review does not reopen it.

## Immutable identities

- Corrected claim `movement-contact-record-lifecycle.corrected-r1.md`: SHA-256 `8638a21aac2fbaeab2c6c9b2eaef7d23684a562f585e6e78539b9bfbaed58e43`.
- Its adjacent checksum file `movement-contact-record-lifecycle.corrected-r1.sha256`: actual raw SHA-256 `7eaea50345e33d3dd805395429cd5a6486205fbc21abd81790097afb21afd5d9`. Its exact UTF-8 text is ``8638a21aac2fbaeab2c6c9b2eaef7d23684a562f585e6e78539b9bfbaed58e43 *movement-contact-record-lifecycle.corrected-r1.md`` and matches the candidate bytes.
- Prior lifecycle claim `movement-contact-record-lifecycle.md`: SHA-256 `902110291be5a2340bf01b02e511a317eb5fb478d7471a1c3617bba1182c5777`.
- Frozen independent source roster `source-roster.md`: SHA-256 `9654d43b1877882fc2652106349c21fe9f42245168ab73fe4416b8a7dcea52a6`.
- Prior independent audit `independent-audit.md`: SHA-256 `58a53be3899815a10364bbcf526db548c0b375cb43f120ec2d1e33d68aba14a0`.
- Prior lifecycle verdict `verdict-lifecycle.txt`: SHA-256 `ab4a6601a2ee7fadd77e55a7a2da94b348a428b6be963d1fe1b27598d243d127`.
- Unchanged traversal claim `movement-contact-block-cell-traversal.md`: SHA-256 `50d7567e8f4bfdda0cd45f35eef68a3403a55b61bd421b3c68add76be87bb9c0`.
- Prior traversal verdict `verdict-block-cell-traversal.txt`: SHA-256 `1afd618c8e5f9579a2dcc06a54bbad159647e779cc0d510db761e72251c1513c`.
- Submitted source checksum evidence `sources.sha256`: SHA-256 `df6165295e7a2b039e09aeea74694f48e9c958e771df76233d52d447b1d94d60`.
- Prior verdict manifest `verdicts.sha256`: SHA-256 `bb85485ce625f993fe5fe3ed3bf3dc488481d55dab3de71e9321a34b8847caad`.

## Rebound checks

The r1 text explicitly makes automatic append conditional on `isAffectedByBlocks()` and states its exact `!isRemoved() && !noPhysics` predicate. It states the exact `isControlledByLocalInstance()` branch: a controlling passenger that is a `Player` uses `isLocalPlayer()`, otherwise the method falls back to `isEffectiveAi()`. It distinguishes the zero-limited piston return inside `Entity.move` from the later, conditional `LivingEntity.tick` fallback append after travel. These corrections match the prior source audit and close its stated qualifications.

The record creation, live-list versus copy-and-clear lifecycle, record-specific AABB, LocalPlayer reachability and bounded one-segment qualification remain within the prior audited claim. The r1 addition that a closed block-effects gate leaves the tick accumulator uncleared is consistent with both releases' enclosing `isAffectedByBlocks()` branch. The claim remains limited to the LocalPlayer/Entity lifecycle scope and does not establish full-pair coverage, an intermediate patch boundary, solid-collision behavior, or runtime parity.

## Unchanged paired claim

The approved `movement-contact-block-cell-traversal.md` revision remains byte-identical to the previously audited SHA listed above, and its prior independent **ACCEPT** verdict remains bound to that immutable revision. No new traversal review is asserted here.