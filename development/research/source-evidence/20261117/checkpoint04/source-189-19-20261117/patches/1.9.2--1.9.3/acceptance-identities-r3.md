# Evidence and audit identities — 1.9.2 → 1.9.3 (r3)

## Source and revision identities

- Pair: `1.9.2--1.9.3`; source namespace: `ornithe-feather`.
- 1.9.2 provenance SHA-256: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- 1.9.3 provenance SHA-256: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- Prior coverage `coverage-r2.md`: SHA-256 `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A`.
- Revised coverage `coverage-r3.md`: SHA-256 `E1D3BC5FD6D778C87CB9337884440D7E742B1DB493A8D51C09E1F35F71E9A636`.
- Candidate `findings/player-tick-world-limit-r2.md`: SHA-256 `B4B0ED48F380356ABCD47663C1B656A2EEFC39738EE5DE745B54102960456514`.

## Audit verdict identities

- Full-pair audit r2, `audit-r2/verdict.md`: SHA-256 `DE54085FF2A6D5BD47693A395D11A6835F3C145933406F945CC31ADC6B43D0AD`; verdict REQUEST CHANGES.
- Conditional query report, `development/research/source-evidence/20261117/checkpoint04/source-189-19-20261117/patches/1.9.2--1.9.3/world-query-limit-followup.md`: SHA-256 `5BFBA153A7AC91BE980B3126FA38FA59F1056116F02C0F22F2260A1B003F1CE8`.
- Bounded query follow-up audit, `world-query-followup-audit/verdict.md`: SHA-256 `FB2F3CC8813782B5324FCA5FE10F386F36CC93BA92F0B6F1568EACB1B82BBA26`; verdict ACCEPT, conditional source observation only.
- Full-pair audit r3, `audit-r3/full-pair-verdict.md`: SHA-256 `CD078FE7396D09601A972CA60CB19526517BBBC2AB6435F537DFDD9943D0F180`; verdict REQUEST CHANGES for pair freeze.

## Correction to the r2 identity pointer

The prior `acceptance-identities-r2.md` file (SHA-256 `5E2D9B670838D1F3EC36AE26CD96795AAE0B9F712465F76A7110517A180F3436`) points to audit SHA `A065D6583B5403F853A60324E0C02209F6CD692FFF9188D1D886F13065B16B01`. The r3 full-pair verdict identifies that as the older r1 REQUEST CHANGES verdict. The current r2 full-pair audit identity is `DE54085FF2A6D5BD47693A395D11A6835F3C145933406F945CC31ADC6B43D0AD`, and its verdict is also REQUEST CHANGES. The only ACCEPT bound here is the separate, bounded query follow-up verdict above; it accepts only the conditional cached-state source observation and not player/query reachability, normal cache population, trajectory significance, or pair freeze.

No pair-wide ACCEPT or freeze is claimed. Coverage r3 retains the query applicability gates as open and requires a fresh independent full-pair audit after coverage reconciliation.

