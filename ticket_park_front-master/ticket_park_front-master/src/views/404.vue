<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
 
</script>
<template>
    <div class="py-18">
        <el-result
            icon="warning"
            title="页面找不到了"
            sub-title="你找的页面走丢了~"
        >
            <template #extra>
            <el-button type="primary" size="medium" @click="$router.back()">返回</el-button>
            </template>
        </el-result>
    </div>
</template>
 
<style scoped>
</style>