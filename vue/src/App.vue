<template>
  <div class="app">
    <div class="container">
      <h1>📄 파일 읽기 도구</h1>
      
      <div class="input-group">
        <input 
          v-model="filePath"
          type="text" 
          id="filePath"
          placeholder="파일 경로를 입력하세요 (예: C:/test/test.txt)"
          autocomplete="off"
          @keypress.enter="readFile"
          :disabled="isLoading"
        >
        <button 
          id="readBtn"
          @click="readFile"
          :disabled="isLoading"
        >
          {{ isLoading ? '읽는 중...' : '읽기' }}
        </button>
      </div>

      <p class="info-text">전체 경로를 입력하고 '읽기' 버튼을 클릭하세요.</p>

      <div 
        id="status"
        v-if="statusMessage"
        :class="['status', statusType]"
        v-html="statusMessage"
      ></div>

      <div 
        id="content"
        :class="['content-area', { empty: !fileContent }]"
      >
        {{ fileContent || '파일 경로를 입력하고 읽기 버튼을 클릭하세요' }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const filePath = ref('')
const fileContent = ref('')
const isLoading = ref(false)
const statusMessage = ref('')
const statusType = ref('success')

const readFile = async () => {
  const path = filePath.value.trim()

  if (!path) {
    showStatus('파일 경로를 입력해주세요.', 'error')
    return
  }

  isLoading.value = true
  showStatus('<span class="spinner"></span>파일을 읽는 중입니다...', 'loading')
  fileContent.value = ''

  try {
    const response = await axios.get('/api/files/read-lines', {
      params: { path }
    })

    const content = response.data

    if (!content || content.trim() === '') {
      showStatus('파일이 비어있습니다.', 'success')
      fileContent.value = '(파일 내용이 없습니다)'
    } else {
      const fileSize = formatFileSize(new Blob([content]).size)
      showStatus(`✓ 파일을 성공적으로 읽었습니다. (${fileSize})`, 'success')
      fileContent.value = content
    }
  } catch (error) {
    console.error('Error:', error)
    showStatus(`✗ 오류 발생: ${error.message}. 파일 경로를 다시 확인해주세요.`, 'error')
    fileContent.value = '파일을 읽을 수 없습니다.'
  } finally {
    isLoading.value = false
  }
}

const showStatus = (message, type) => {
  statusMessage.value = message
  statusType.value = type
}

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i]
}
</script>

<style scoped>
.app {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  max-width: 900px;
  width: 100%;
  padding: 40px;
}

h1 {
  color: #333;
  margin-bottom: 30px;
  text-align: center;
  font-size: 28px;
}

.input-group {
  display: flex;
  gap: 10px;
  margin-bottom: 30px;
}

input[type="text"] {
  flex: 1;
  padding: 12px 15px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.3s ease;
  font-family: inherit;
}

input[type="text"]:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

input[type="text"]:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
}

button {
  padding: 12px 30px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
  font-family: inherit;
}

button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
}

button:active:not(:disabled) {
  transform: translateY(0);
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.content-area {
  background: #f8f9fa;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  min-height: 300px;
  max-height: 600px;
  overflow-y: auto;
  font-family: 'Courier New', monospace;
  white-space: pre-wrap;
  word-wrap: break-word;
  color: #333;
  line-height: 1.6;
}

.content-area.empty {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #999;
  font-style: italic;
}

.status {
  margin-top: 15px;
  padding: 10px 15px;
  border-radius: 6px;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.status.success {
  background: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.status.error {
  background: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}

.status.loading {
  background: #d1ecf1;
  color: #0c5460;
  border: 1px solid #bee5eb;
}

.spinner {
  display: inline-block;
  width: 12px;
  height: 12px;
  border: 2px solid #0c5460;
  border-top-color: transparent;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.info-text {
  color: #666;
  font-size: 12px;
  margin-top: 8px;
  text-align: center;
}

@media (max-width: 600px) {
  .input-group {
    flex-direction: column;
  }

  .container {
    padding: 25px;
  }

  h1 {
    font-size: 22px;
  }

  .content-area {
    min-height: 250px;
    max-height: 400px;
  }
}
</style>
