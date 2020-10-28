package com.example.docscan.clickFunctionality

import com.example.docscan.database.DocsEntity

interface OnImageClickInterface{
    fun onImageClick(position: Int, get: DocsEntity)
}