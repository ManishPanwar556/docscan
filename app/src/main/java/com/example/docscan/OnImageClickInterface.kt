package com.example.docscan

import com.example.docscan.database.DocsEntity

interface OnImageClickInterface{
    fun onImageClick(position: Int, get: DocsEntity)
}