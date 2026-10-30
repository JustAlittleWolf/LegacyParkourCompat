$out = 'C:\Users\Wolfi\.codex\worktrees\56a8\LegacyParkourCompat\.native-source\20261030\collision-pool\fluid-author'
$oldPath = 'C:\Users\Wolfi\.codex\worktrees\56a8\LegacyParkourCompat\.native-source\20261028\collision-next\dda-189-low-projection-000-031\metadata-current-projection.tsv'
$currentPath = 'C:\Users\Wolfi\.codex\worktrees\56a8\LegacyParkourCompat\.native-source\20261028\collision-next\dda-current-state-projection-20261029\block-state-data-current-name-projection.jsonl'
$oldRows = Import-Csv -Delimiter "`t" $oldPath | Where-Object { $_.legacy_id -in '8', '9', '10', '11' }
$current = @{}
Get-Content $currentPath | ForEach-Object {
    $row = ConvertFrom-Json $_
    if ($row.oldId -ge 8 -and $row.oldId -le 11) {
        $current["$($row.oldId):$($row.metadata)"] = $row
    }
}
$headers = 'old_id','old_registry_name','old_instance_class','meta','old_decode_validity','old_decoded_tuple','DF_raw_index','DF_lookup_kind','BlockStateData_source_line','DF_target_name_tuple','DF_old_alias_identity','ordered_rename_trace','post_rename_name','chunk_filter_to_air','chunk_storage_flags','special_state_fix_trace','later_state_fix_trace','final_current_registry_key','current_registered','current_state_codec_result'
$lines = [System.Collections.Generic.List[string]]::new()
$lines.Add(($headers -join "`t"))
foreach ($oldRow in ($oldRows | Sort-Object { [int]$_.legacy_id }, { [int]$_.metadata })) {
    $row = $current["$($oldRow.legacy_id):$($oldRow.metadata)"]
    $instance = switch ($oldRow.legacy_id) {
        '8' { 'FlowingLiquidBlock' }
        '9' { 'LiquidSourceBlock' }
        '10' { 'FlowingLiquidBlock' }
        '11' { 'LiquidSourceBlock' }
    }
    $aliases = (@($row.legacyAliases) | ForEach-Object { "$($_.name) {level=$($_.properties.level)}" }) -join ';'
    $renames = (@($row.renameTrace) | ForEach-Object { "#$($_.order)@$($_.line):$($_.from)->$($_.to)" }) -join ';'
    if ([string]::IsNullOrEmpty($renames)) { $renames = 'none' }
    $levelTuple = "LEVEL=$($oldRow.metadata)"
    $targetTuple = "level=$($row.targetProperties.level)"
    $codec = "name resolves registered block; Properties $targetTuple decoded by StateHolder/BlockState codec; LEVEL IntProperty 0..15"
    $values = @(
        $oldRow.legacy_id, $oldRow.legacy_key, $instance, $oldRow.metadata,
        $oldRow.old_decode_classification, $levelTuple, $row.blockDataIndex,
        $row.lookup, $row.sourceLine, "$($row.targetName) {$targetTuple}", $aliases,
        $renames, $row.postRenameName, [string]$row.chunkFilterToAir,
        ($row.chunkStorageFixFlags | ConvertTo-Json -Compress), 'none', 'none',
        $row.postRenameName, [string]$row.currentRegistryName, $codec
    )
    $safe = foreach ($value in $values) {
        ([string]$value).Replace("`t", ' ').Replace("`r", ' ').Replace("`n", ' ')
    }
    $lines.Add(($safe -join "`t"))
}
[IO.File]::WriteAllLines((Join-Path $out 'fluid-state-projection.tsv'), $lines, [Text.UTF8Encoding]::new($false))
